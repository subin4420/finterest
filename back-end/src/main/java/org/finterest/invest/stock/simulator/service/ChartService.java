package org.finterest.invest.stock.simulator.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import okhttp3.*;
import org.finterest.invest.stock.simulator.mapper.SimulatorMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Log4j
@Service
@RequiredArgsConstructor
public class ChartService {

    @Value("${api.appKey}")
    private String appKey;

    @Value("${api.appSecret}")
    private String appSecret;

    private final SimulatorMapper mapper;

    public List<List<String>> sendChartData(String stockCode) {
        List<List<String>> chartData = new ArrayList<>();

        String token = getOauthToken();

        System.out.println(token);
        // 현재 시간
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss");
        String curTime = currentTime.format(formatter); // 현재 시간

        // URL 생성
        String url = "https://openapivts.koreainvestment.com:29443/uapi/domestic-stock/v1/quotations/inquire-time-itemchartprice?FID_ETC_CLS_CODE=&FID_COND_MRKT_DIV_CODE=J&FID_INPUT_ISCD="
                + stockCode
                + "&FID_INPUT_HOUR_1="
                + curTime
                + "&FID_PW_DATA_INCU_YN=Y";

        // OkHttp 클라이언트 생성
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("content-type", "application/json")
                .addHeader("authorization", "Bearer " + token)
                .addHeader("appkey", appKey)
                .addHeader("appsecret", appSecret)
                .addHeader("tr_id", "FHKST03010200")
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseData = response.body().string();
                JSONObject jsonResponse = new JSONObject(responseData);
                JSONArray dataArray = jsonResponse.getJSONArray("output2"); // API에서 반환되는 데이터에 따라 이 키 값을 수정

                // JSON 데이터를 List<List<String>> 형태로 변환
                // 각 항목 순회하며 데이터 추출 및 리스트에 저장
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject dataObj = dataArray.getJSONObject(i);
                    List<String> row = new ArrayList<>();

                    row.add(dataObj.getString("stck_cntg_hour"));   // 시간
                    row.add(dataObj.getString("stck_oprc"));  // 현재가
                    row.add(dataObj.getString("stck_prpr"));   // 시가
                    row.add(dataObj.getString("stck_lwpr"));    // 저가
                    row.add(dataObj.getString("stck_hgpr"));   // 고가
                    row.add(dataObj.getString("cntg_vol")); // 거래량

                    chartData.add(row);
                }
            } else {
                System.out.println("Request Failed: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        chartData.sort(Comparator.comparing(row -> row.get(0)));
        return chartData;
    }

    @Scheduled(fixedRate = 6 * 60 * 60 * 1000)
    public String updateOauthToken() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        String accessToken = null;
        String result = "";

        try{
        MediaType mediaType = MediaType.parse("application/json");
        String requestBodyJson = String.format("{\r\n    \"grant_type\": \"client_credentials\",\r\n    \"appkey\": \"%s\",\r\n    \"appsecret\": \"%s\"\r\n}", appKey, appSecret);
        RequestBody body = RequestBody.create(mediaType, requestBodyJson);
        Request request = new Request.Builder()
                .url("https://openapivts.koreainvestment.com:29443/oauth2/tokenP")
                .method("POST", body)
                .addHeader("content-type", "application/json")
                .build();
        Response response = client.newCall(request).execute();

        String responseData = response.body().string();

        JSONObject jsonObject = new JSONObject(responseData);

        // access_token 추출
        accessToken = jsonObject.getString("access_token");
            int updatedRows = mapper.updateOauthToken(accessToken);
            if (updatedRows > 0) {
                System.out.println("OAuth 토큰 업데이트 성공");
            } else {
                System.out.println("OAuth 토큰 업데이트 실패");
            }

        result = "Token successfully updated";
        } catch (IOException e) {
            System.out.println("I/O 예외 발생: " + e.getMessage());
            e.printStackTrace();
            result = "Token update failed";
        }
        return result;
    }

    public void loadData() {
        updateOauthToken();
    }

    public String getOauthToken() {
        return mapper.getOauthToken();
    }
}