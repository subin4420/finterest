package org.finterest.invest.stock.simulator.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import okhttp3.*;
import org.finterest.invest.scenario.mapper.ScenarioMapper;
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
import java.util.Random;

@Log4j
@Service
@RequiredArgsConstructor
public class ChartService {

    private final SimulatorMapper mapper;
    private final ScenarioMapper scenarioMapper;

    @Value("${api.appKey}")
    private String appKey;

    @Value("${api.appSecret}")
    private String appSecret;

    public List<List<String>> sendChartData(String stockCode) throws IOException {
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

    @Scheduled(fixedRate = 43200000)
    String updateOauthToken() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\r\n    \"grant_type\": \"client_credentials\",\r\n    \"appkey\": \"PSRXymebdmx9Kvgesb6qEHaj3zo5j6FHIftE\",\r\n    \"appsecret\": \"1JhEewe7fshUrv42mE0enQSzTRIj/awR2RImFyplwmUiu3mDYrh5quUSna1Stdkw4JFOqJMT/gkwj05e8grWAjHUM+t8EOsp1Lx48L4uVA1t/bY5oUQuGd5h4D5Dg8A7zHQxFWkNfiewHEVJXguLWrcHxRC2j0zKdTcZgg1p3wyqBqJ1vG0=\"\r\n}");
        Request request = new Request.Builder()
                .url("https://openapivts.koreainvestment.com:29443/oauth2/tokenP")
                .method("POST", body)
                .addHeader("content-type", "application/json")
                .build();
        Response response = client.newCall(request).execute();

        String responseData = response.body().string();

        JSONObject jsonObject = new JSONObject(responseData);

        // access_token 추출
        String accessToken = jsonObject.getString("access_token");
        return mapper.updateOauthToken(accessToken);
    }

    public List<List<String>> sendHomeChartData() {
        // 주식 코드 랜덤 호출
        Random random = new Random();
        int randomId = random.nextInt(5) + 1;
        String stockCode = scenarioMapper.selectStockCode(randomId);

        // 데이터를 담을 객체 선언
        List<List<String>> chartData = new ArrayList<>();

        // API 호출 시 필요한 토큰 호출
        String oauthToken = getOauthToken();

        // 현재 날짜와 100일전 날짜 계산
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String curTime = currentTime.format(formatter); // 현재 시간
        LocalDateTime hundredDaysAgo = currentTime.minusDays(100); //100일 전
        String agoTime = hundredDaysAgo.format(formatter);



        // 호출 할 Open API url 생성
        String url = "https://openapivts.koreainvestment.com:29443/uapi/domestic-stock/v1/quotations/inquire-daily-itemchartprice?fid_cond_mrkt_div_code=J&fid_input_iscd="
                + stockCode
                + "&fid_input_date_1="
                + agoTime
                + "&fid_input_date_2="
                + curTime
                + "&fid_period_div_code=D&fid_org_adj_prc=1";

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("content-type", "application/json")
                .addHeader("authorization", "Bearer " + oauthToken)
                .addHeader("appkey", appKey)
                .addHeader("appsecret", appSecret)
                .addHeader("tr_id", "FHKST03010100")
                .build();
        try{
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                JSONObject jsonResponse = new JSONObject(responseBody);
                JSONArray results = jsonResponse.getJSONArray("output2");

                for (int i = 0; i < results.length(); i++) {
                    JSONObject result = results.getJSONObject(i);
                    List<String> row = new ArrayList<>();

                    row.add(result.getString("stck_bsop_date"));   // 시간
                    row.add(result.getString("stck_oprc"));  // 시가
                    row.add(result.getString("stck_clpr"));   // 종가
                    row.add(result.getString("stck_lwpr"));    // 저가
                    row.add(result.getString("stck_hgpr"));   // 고가
                    row.add(result.getString("acml_vol")); // 거래량

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

    public String getOauthToken() {
        return mapper.getOauthToken();
    }
}