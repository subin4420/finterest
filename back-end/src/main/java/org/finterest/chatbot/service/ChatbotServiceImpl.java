package org.finterest.chatbot.service;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatbotServiceImpl implements ChatbotService{

    @Value("${openai.api.key}")
    private String apiKey;

    private static final String BASE_URL = "https://api.openai.com/v1/chat/completions";

    @Override
    public String getStockPrice(String query) {
        String result = null;
        try {
            String toolsJson = "["
                    + "  {"
                    + "    \"type\": \"function\","
                    + "    \"function\": {"
                    + "      \"name\": \"get_stock_price\","
                    + "      \"description\": \"Get the current stock price'\","
                    + "      \"parameters\": {"
                    + "        \"type\": \"object\","
                    + "        \"properties\": {"
                    + "          \"symbol\": {"
                    + "            \"type\": \"string\","
                    + "            \"description\": \"The stock symbol\""
                    + "          }"
                    + "        },"
                    + "        \"required\": [\"symbol\"],"
                    + "        \"additionalProperties\": false"
                    + "      }"
                    + "    }"
                    + "  }"
                    + "]";

            List<Map<String, String>> messages = new ArrayList<>();

            // First message (system)
            Map<String, String> systemMessage = new HashMap<>();
            systemMessage.put("role", "system");
            systemMessage.put("content", "");
            messages.add(systemMessage);

            // Second message (user)
            Map<String, String> userMessage = new HashMap<>();
            userMessage.put("role", "user");
            userMessage.put("content", query);
            messages.add(userMessage);

            // Convert toolsJson to JsonArray
            JsonArray toolsArray = JsonParser.parseString(toolsJson).getAsJsonArray();

            // Create request body map
            Map<String, Object> requestBodyMap = Map.of(
                    "model", "gpt-4o",
                    "messages", messages,
                    "tools", toolsArray
            );

            // Initialize OkHttpClient
            OkHttpClient client = new OkHttpClient().newBuilder().build();

            // Convert request body to JSON string
            Gson gson = new Gson();
            String jsonBody = gson.toJson(requestBodyMap);

            // Create request body for the API call
            RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json"));

            // Build the request
            Request request = new Request.Builder()
                    .url(BASE_URL)
                    .post(body)
                    .addHeader("Authorization", "Bearer " + apiKey)
                    .addHeader("Content-Type", "application/json")
                    .build();

            // Execute request and get response
            Response response = client.newCall(request).execute();
            result = response.body().string();

            // Parse the JSON response
            JsonObject jsonObject = JsonParser.parseString(result).getAsJsonObject();
            JsonObject messageObject = jsonObject
                    .getAsJsonArray("choices")
                    .get(0).getAsJsonObject()
                    .getAsJsonObject("message");

            if (messageObject.has("tool_calls") && !messageObject.get("tool_calls").isJsonNull()) {
                // Extract argument from tool_calls if not null
                String argumentsString = messageObject
                        .getAsJsonArray("tool_calls")
                        .get(0)
                        .getAsJsonObject()
                        .getAsJsonObject("function")
                        .get("arguments")
                        .getAsString();
                JsonObject argumentsJson = JsonParser.parseString(argumentsString).getAsJsonObject();
                String argument = argumentsJson.get("symbol").getAsString().split("\\.")[0];

                //return argument;
                // 실시간 주식 가격 API 호출
                String stockPrice = fetchRealTimeStockPrice(argument);
                return "실시간 주식 가격: " + argument + ", Price: " + stockPrice+"입니다";
            } else {
                // If no tool_calls, return content
                result = messageObject.get("content").getAsString();
                System.out.println(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 새로운 메서드: 주식 심볼을 통해 실시간 주식 가격 가져오기
    private String fetchRealTimeStockPrice(String symbol) throws Exception {
        String apiKey = "YOUR_ALPHAVANTAGE_API_KEY";  // 여기에 실제 API 키 입력
        String apiUrl = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=" + symbol + "&interval=1min&apikey=" + apiKey;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(apiUrl)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new Exception("API 호출 실패: " + response);
        }

        String responseBody = response.body().string();
        JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();

        // 주식 가격 정보 파싱 (Alpha Vantage 응답 형식에 맞춰 파싱)
        JsonObject timeSeries = jsonResponse.getAsJsonObject("Time Series (1min)");
        if (timeSeries != null) {
            JsonObject latestData = timeSeries.entrySet().iterator().next().getValue().getAsJsonObject();
            return latestData.get("1. open").getAsString();  // 주식 가격 (open 가격)
        } else {
            throw new Exception("주식 가격 데이터를 찾을 수 없음");
        }
    }

}
