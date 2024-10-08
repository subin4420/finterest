package org.finterest.invest.scenario.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import okhttp3.*;
import org.finterest.invest.scenario.domain.ScenarioChartDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.finterest.invest.scenario.domain.ScenarioConversionVO;
import org.finterest.invest.scenario.domain.ScenarioVO;
import org.finterest.invest.scenario.mapper.ScenarioMapper;
import org.finterest.invest.stock.simulator.service.ChartService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Log4j
@Service
@RequiredArgsConstructor
public class ScenarioService {

    @Value("${api.appKey}")
    private String appKey;

    @Value("${api.appSecret}")
    private String appSecret;

    private final ScenarioMapper scenarioMapper;
    private final ChartService chartService;
    private final ScenarioChartDTO chartDTO = new ScenarioChartDTO();
    private final ScenarioConversionVO scenarioConversionVO = new ScenarioConversionVO();

    // 모든 시나리오 가져오기
    public List<ScenarioVO> getAllScenarios() {
        return scenarioMapper.getAllScenarios();
    }

    // 모든 시나리오 변환 가져오기
    public List<ScenarioConversionVO> getAllScenarioConversions() {
        return scenarioMapper.getAllScenarioConversions();
    }

    // 차트에 그릴 데이터 API호출로 가져오고 데이터 변환 후 프론트에 넘김
    public List<List<String>> sendScenarioChartData(String stockCode) {
        List<List<String>> chartData = new ArrayList<>();

        String token = chartService.getOauthToken();

        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String curTime = currentTime.format(formatter); // 현재 시간
        LocalDateTime hundredDaysAgo = currentTime.minusDays(100); //100일 전
        String agoTime = hundredDaysAgo.format(formatter);

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
                    .addHeader("authorization", "Bearer " + token)
                    .addHeader("appkey", appKey)
                    .addHeader("appsecret", appSecret)
                    .addHeader("tr_id", "FHKST03010100")
                    .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                JSONObject jsonResponse = new JSONObject(responseBody);
                JSONArray results = jsonResponse.getJSONArray("output2");

                for (int i = 0; i < results.length(); i++) {
                    JSONObject result = results.getJSONObject(i);
                    List<String> row = new ArrayList<>();

                    row.add(result.getString("stck_bsop_date")); //일자
                    row.add(result.getString("stck_oprc"));//시가
                    row.add(result.getString("stck_clpr")); //종가
                    row.add(result.getString("stck_lwpr"));//최저가
                    row.add(result.getString("stck_hgpr"));//최고가

                    chartData.add(row);

                    if(i==0) {
                        chartDTO.setStck_bsop_date(row.get(0));
                        chartDTO.setStck_oprc(row.get(1));
                        chartDTO.setStck_clpr(row.get(2));
                        chartDTO.setStck_lwpr(row.get(3));
                        chartDTO.setStck_hgpr(row.get(4));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        chartData.sort(Comparator.comparing(row -> row.get(0)));
        return chartData;
    }

    public ScenarioChartDTO currentData() {
        return chartDTO; // 전역적으로 유지된 chartDTO 객체를 반환
    }

    public Map<String, Object> nextTurn() {
        Map<String, Object> result = new HashMap<>();

        String curDate = chartDTO.getStck_bsop_date();
        String curOprc = chartDTO.getStck_oprc();
        String curClpr = chartDTO.getStck_clpr();
        String curLwpr = chartDTO.getStck_lwpr();
        String curHgpr = chartDTO.getStck_hgpr();

        // 다음 턴 날짜 구하는 부분
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd"); // curDate의 포맷에 맞게 패턴 설정
        LocalDate parsedDate = LocalDate.parse(curDate, formatter); // String을 LocalDate로 변환
        LocalDate nextDateData = parsedDate.plusDays(1); // plusDays(1)로 하루 추가
        String nextDate = nextDateData.format(formatter);

        // 랜덤 시나리오 추출
        Random random = new Random();
        int randomId = random.nextInt(20) + 1;
        ScenarioVO vo = selectScenario(randomId);
        double effect;
        if(vo.getMarketSector().equals(scenarioConversionVO.getMarketSector())) {
            effect = vo.getStockEffect();
        } else {
            effect = vo.getGlobalEffect();
        }

        double randomFactor = (Math.random() * 2) - 1; // -5.00부터 5.00까지의 랜덤 값 생성

        // 변동률 반영
        double nextOprc = Double.parseDouble(curOprc) * (1 + (effect / 100)) * (1 + (randomFactor / 100));
        double nextClpr = Double.parseDouble(curClpr) * (1 + (effect / 100));
        double nextLwpr = Double.parseDouble(curLwpr) * (1 + (effect / 100)) * (1 + (randomFactor / 100));
        double nextHgpr = Double.parseDouble(curHgpr) * (1 + (effect / 100)) * (1 + (randomFactor / 100));

        // 100의 자리에서 반올림
        nextOprc = Math.round(nextOprc / 100.0) * 100.0;
        nextClpr = Math.round(nextClpr / 100.0) * 100.0;
        nextLwpr = Math.round(nextLwpr / 100.0) * 100.0;
        nextHgpr = Math.round(nextHgpr / 100.0) * 100.0;

        // DTO에 저장
        chartDTO.setStck_bsop_date(nextDate);
        chartDTO.setStck_oprc(String.format("%.0f", nextOprc));
        chartDTO.setStck_clpr(String.format("%.0f", nextClpr));
        chartDTO.setStck_lwpr(String.format("%.0f", nextLwpr));
        chartDTO.setStck_hgpr(String.format("%.0f", nextHgpr));
        String description = vo.getDescription();

        result.put("description", description);
        result.put("stck_bsop_date", chartDTO.getStck_bsop_date());
        result.put("stck_oprc", chartDTO.getStck_oprc());
        result.put("stck_clpr", chartDTO.getStck_clpr());
        result.put("stck_lwpr", chartDTO.getStck_lwpr());
        result.put("stck_hgpr", chartDTO.getStck_hgpr());

        return result;
    }

    public ScenarioVO selectScenario(int randomId) {
        return scenarioMapper.selectScenario(randomId);
    }

    public String[] selectStockData() {
        Random random = new Random();
        int randomId = random.nextInt(5) + 1;

        String[] results = new String[2];

        String stockName = scenarioMapper.selectStockName(randomId);
        String stockCode = scenarioMapper.selectStockCode(randomId);
        String marketSector = scenarioMapper.selectMarketSector(randomId);

        scenarioConversionVO.setMarketSector(marketSector);

        results[0] = stockName;
        results[1] = stockCode;

        return  results;
    }
}