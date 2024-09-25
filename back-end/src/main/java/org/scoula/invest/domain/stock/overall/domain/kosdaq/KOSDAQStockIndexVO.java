package org.scoula.invest.domain.stock.overall.domain.kosdaq;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class KOSDAQStockIndexVO {
    private Long id;
    private String basDt;         // 기준일
    private String idxNm;         // 지수명
    private String idxCsf;        // 지수 구분
    private String epyItmsCnt;    // 종목수
    private String clpr;          // 종가
    private String vs;            // 대비
    private String fltRt;         // 등락률
    private String mkp;           // 시가
    private String hipr;          // 최고가
    private String lopr;          // 최저가
    private String trqu;          // 거래량
    private String trPrc;         // 거래대금
    private String lstgMrktTotAmt;// 상장 시장 총액
    private String lsYrEdVsFltRg; // 전년도 대비 변동폭
    private String lsYrEdVsFltRt; // 전년도 대비 변동률
    private String yrWRcrdHgst;   // 연중 최고가
    private String yrWRcrdHgstDt; // 연중 최고가 날짜
    private String yrWRcrdLwst;   // 연중 최저가
    private String yrWRcrdLwstDt; // 연중 최저가 날짜
    private String basPntm;       // 기준 시간
    private String basIdx;        // 기준 지수
}