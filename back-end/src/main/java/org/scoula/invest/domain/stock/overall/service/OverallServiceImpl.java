package org.scoula.invest.domain.stock.overall.service;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.scoula.invest.domain.stock.overall.dto.kospi.KOSPIStockIndexDTO;
import org.scoula.invest.domain.stock.overall.dto.kospi.KOSPIStockListDTO;
import org.scoula.invest.domain.stock.overall.mapper.OverallMapper;
import org.scoula.invest.domain.stock.overall.service.OverallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class OverallServiceImpl implements OverallService {

    @Autowired
    private OverallMapper overallMapper;

    @Value("${api.serviceKey}")
    private String serviceKey;

    private final OkHttpClient client = new OkHttpClient();

    private final String KOSPI_STOCK_LIST_URL = "http://apis.data.go.kr/1160100/service/GetStockSecuritiesInfoService/getStockPriceInfo";
    private final String KOSPI_STOCK_INDEX_URL = "http://apis.data.go.kr/1160100/service/GetMarketIndexInfoService/getStockMarketIndex";

    // KOSPI 주식 리스트 저장 메소드
    @Override
    public String fetchAndSaveKOSPIStockList() {
        try {
            String yesterday = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd")); // 전날 날짜

            UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(KOSPI_STOCK_LIST_URL)
                    .queryParam("serviceKey", serviceKey)
                    .queryParam("numOfRows", 1000)
                    .queryParam("pageNo", 1)
                    .queryParam("resultType", "json")
                    .queryParam("basDt", yesterday)  // 전날 날짜
                    .queryParam("mrktCls", "KOSPI")
                    .build();

            String requestUrl = uriBuilder.toUriString();
            System.out.println("Generated Request URL: " + requestUrl);

            // OkHttpClient를 사용하여 요청
            Request request = new Request.Builder()
                    .url(requestUrl)
                    .get()
                    .addHeader("Cookie", "SCOUTER=x1fe8444r2npje")
                    .build();

            Response response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            // 응답 데이터 가져오기
            String responseData = response.body().string();

            // JSON 파싱 및 데이터 저장 로직
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(responseData);
            JSONObject responseObj = (JSONObject) jsonObject.get("response");
            JSONObject body = (JSONObject) responseObj.get("body");
            JSONObject items = (JSONObject) body.get("items");
            JSONArray itemArray = (JSONArray) items.get("item");

            for (Object obj : itemArray) {
                JSONObject tmp = (JSONObject) obj;
                String stockName = (String) tmp.get("itmsNm");

                // 우선주를 제외하는 조건 추가 (우선주의 경우 '우'로 끝나거나 '우B', '우C' 등으로 끝남)
                if (stockName.endsWith("우") || stockName.endsWith("우B") || stockName.endsWith("우C")) {
                    continue; // 우선주일 경우 다음 루프로 넘어감
                }

                KOSPIStockListDTO dto = KOSPIStockListDTO.builder()
                        .basDt((String) tmp.get("basDt"))
                        .srtnCd((String) tmp.get("srtnCd"))
                        .isinCd((String) tmp.get("isinCd")) // 이미 포함됨
                        .itmsNm(stockName)
                        .mrktCtg((String) tmp.get("mrktCtg"))
                        .clpr((String) tmp.get("clpr"))
                        .vs((String) tmp.get("vs"))
                        .fltRt((String) tmp.get("fltRt"))
                        .mkp((String) tmp.get("mkp"))
                        .hipr((String) tmp.get("hipr"))
                        .lopr((String) tmp.get("lopr"))
                        .trqu((String) tmp.get("trqu"))
                        .trPrc((String) tmp.get("trPrc"))
                        .lstgStCnt((String) tmp.get("lstgStCnt"))
                        .mrktTotAmt((String) tmp.get("mrktTotAmt"))
                        .build();

                overallMapper.insertKOSPIStockList(dto);
            }

            return "KOSPI Stock List 데이터 저장 성공";

        } catch (Exception e) {
            e.printStackTrace();
            return "KOSPI Stock List 데이터 저장 실패: " + e.getMessage();
        }
    }

    // KOSPI 주식 지수 저장 메소드
    @Override
    public String fetchAndSaveKOSPIStockIndex() {
        try {
            String yesterday = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd")); // 전날 날짜

            UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(KOSPI_STOCK_INDEX_URL)
                    .queryParam("serviceKey", serviceKey)
                    .queryParam("numOfRows", 1000)
                    .queryParam("pageNo", 1)
                    .queryParam("resultType", "json")
                    .queryParam("basDt", yesterday)  // 전날 날짜
                    //.queryParam("idxNm", "코스피")
                    .build();

            String requestUrl = uriBuilder.toUriString();

            // OkHttpClient를 사용하여 요청
            Request request = new Request.Builder()
                    .url(requestUrl)
                    .get()
                    .addHeader("Cookie", "SCOUTER=x1fe8444r2npje")
                    .build();

            Response response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            // 응답 데이터 가져오기
            String responseData = response.body().string();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(responseData);
            JSONObject responseObj = (JSONObject) jsonObject.get("response");
            JSONObject body = (JSONObject) responseObj.get("body");
            JSONObject items = (JSONObject) body.get("items");
            JSONArray itemArray = (JSONArray) items.get("item");

            for (Object obj : itemArray) {
                JSONObject tmp = (JSONObject) obj;
                KOSPIStockIndexDTO dto = KOSPIStockIndexDTO.builder()
                        .basDt((String) tmp.get("basDt"))
                        .idxNm((String) tmp.get("idxNm"))
                        .idxCsf((String) tmp.get("idxCsf"))
                        .clpr((String) tmp.get("clpr"))
                        .vs((String) tmp.get("vs"))
                        .fltRt((String) tmp.get("fltRt"))
                        .mkp((String) tmp.get("mkp"))
                        .epyItmsCnt((String) tmp.get("epyItmsCnt")) // 추가
                        .hipr((String) tmp.get("hipr")) // 추가
                        .lopr((String) tmp.get("lopr")) // 추가
                        .trqu((String) tmp.get("trqu")) // 추가
                        .trPrc((String) tmp.get("trPrc")) // 추가
                        .lstgMrktTotAmt((String) tmp.get("lstgMrktTotAmt")) // 추가
                        .lsYrEdVsFltRg((String) tmp.get("lsYrEdVsFltRg")) // 추가
                        .lsYrEdVsFltRt((String) tmp.get("lsYrEdVsFltRt")) // 추가
                        .yrWRcrdHgst((String) tmp.get("yrWRcrdHgst")) // 추가
                        .yrWRcrdHgstDt((String) tmp.get("yrWRcrdHgstDt")) // 추가
                        .yrWRcrdLwst((String) tmp.get("yrWRcrdLwst")) // 추가
                        .yrWRcrdLwstDt((String) tmp.get("yrWRcrdLwstDt")) // 추가
                        .basPntm((String) tmp.get("basPntm")) // 추가
                        .basIdx((String) tmp.get("basIdx")) // 추가
                        .build();

                overallMapper.insertKOSPIStockIndex(dto);
            }

            return "KOSPI Stock Index 데이터 저장 성공";

        } catch (Exception e) {
            e.printStackTrace();
            return "KOSPI Stock Index 데이터 저장 실패: " + e.getMessage();
        }
    }

    @Scheduled(cron = "0 0 12 * * ?") // 매일 12시 정각에 실행
    public void updateStockData() {
        System.out.println("12시 스케줄러 실행: 데이터 업데이트 시작");

        String indexResult = fetchAndSaveKOSPIStockIndex();
        String listResult = fetchAndSaveKOSPIStockList();

        System.out.println(indexResult);
        System.out.println(listResult);

        System.out.println("12시 스케줄러 실행: 데이터 업데이트 완료");
    }

    @Override
    public List<KOSPIStockIndexDTO> getAllKOSPIStockIndices() {
        return overallMapper.selectAllKOSPIStockIndices();
    }

    @Override
    public List<KOSPIStockListDTO> getAllKOSPIStockLists() {
        return overallMapper.selectAllKOSPIStockLists();
    }

    public void loadData() {
        fetchAndSaveKOSPIStockIndex();
        fetchAndSaveKOSPIStockList();
    }
}
