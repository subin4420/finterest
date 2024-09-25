package org.scoula.invest.domain.stock.overall.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.scoula.invest.domain.stock.overall.dto.kodaq.KOSDAQStockListDTO;
import org.scoula.invest.domain.stock.overall.dto.kospi.KOSPIStockListDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockListResponseDTO {
    private Long id;
    private String basDt;
    private String srtnCd;
    private String isinCd;
    private String itmsNm;
    private String mrktCtg;
    private String clpr;
    private String vs;
    private String fltRt;
    private String mkp;
    private String hipr;
    private String lopr;
    private String trqu;
    private String trPrc;
    private String lstgStCnt;
    private String mrktTotAmt;


    // KOSDAQ 데이터로부터 응답 DTO 생성
    public static StockListResponseDTO fromKOSDAQ(KOSDAQStockListDTO kosdaqDto) {
        return kosdaqDto == null ? null : StockListResponseDTO.builder()
                .id(kosdaqDto.getId())
                .basDt(kosdaqDto.getBasDt())
                .srtnCd(kosdaqDto.getSrtnCd())
                .isinCd(kosdaqDto.getIsinCd())
                .itmsNm(kosdaqDto.getItmsNm())
                .mrktCtg(kosdaqDto.getMrktCtg())
                .clpr(kosdaqDto.getClpr())
                .vs(kosdaqDto.getVs())
                .fltRt(kosdaqDto.getFltRt())
                .mkp(kosdaqDto.getMkp())
                .hipr(kosdaqDto.getHipr())
                .lopr(kosdaqDto.getLopr())
                .trqu(kosdaqDto.getTrqu())
                .trPrc(kosdaqDto.getTrPrc())
                .lstgStCnt(kosdaqDto.getLstgStCnt())
                .mrktTotAmt(kosdaqDto.getMrktTotAmt())
                .build();
    }

    // KOSPI 데이터로부터 응답 DTO 생성
    public static StockListResponseDTO fromKOSPI(KOSPIStockListDTO kospiDto) {
        return kospiDto == null ? null : StockListResponseDTO.builder()
                .id(kospiDto.getId())
                .basDt(kospiDto.getBasDt())
                .srtnCd(kospiDto.getSrtnCd())
                .isinCd(kospiDto.getIsinCd())
                .itmsNm(kospiDto.getItmsNm())
                .mrktCtg(kospiDto.getMrktCtg())
                .clpr(kospiDto.getClpr())
                .vs(kospiDto.getVs())
                .fltRt(kospiDto.getFltRt())
                .mkp(kospiDto.getMkp())
                .hipr(kospiDto.getHipr())
                .lopr(kospiDto.getLopr())
                .trqu(kospiDto.getTrqu())
                .trPrc(kospiDto.getTrPrc())
                .lstgStCnt(kospiDto.getLstgStCnt())
                .mrktTotAmt(kospiDto.getMrktTotAmt())
                .build();
    }
}