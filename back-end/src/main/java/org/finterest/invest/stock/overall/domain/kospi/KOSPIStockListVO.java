package org.finterest.invest.stock.overall.domain.kospi;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KOSPIStockListVO {
    private Long id;
    private String basDt;       // 기준일
    private String srtnCd;      // 종목 코드
    private String isinCd;      // ISIN 코드
    private String itmsNm;      // 종목명
    private String mrktCtg;     // 시장 구분
    private String clpr;        // 종가
    private String vs;          // 대비
    private String fltRt;       // 등락률
    private String mkp;         // 시가
    private String hipr;        // 최고가
    private String lopr;        // 최저가
    private String trqu;        // 거래량
    private String trPrc;       // 거래대금
    private String lstgStCnt;   // 상장 주식 수
    private String mrktTotAmt;  // 시가총액
}