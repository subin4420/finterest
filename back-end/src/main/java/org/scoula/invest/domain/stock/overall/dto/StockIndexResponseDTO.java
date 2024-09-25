package org.scoula.invest.domain.stock.overall.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.scoula.invest.domain.stock.overall.dto.kodaq.KOSDAQStockIndexDTO;
import org.scoula.invest.domain.stock.overall.dto.kospi.KOSPIStockIndexDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockIndexResponseDTO {
    private Long id;
    private String basDt;
    private String idxNm;
    private String idxCsf;
    private String epyItmsCnt;
    private String clpr;
    private String vs;
    private String fltRt;
    private String mkp;
    private String hipr;
    private String lopr;
    private String trqu;
    private String trPrc;
    private String lstgMrktTotAmt;
    private String lsYrEdVsFltRg;
    private String lsYrEdVsFltRt;
    private String yrWRcrdHgst;
    private String yrWRcrdHgstDt;
    private String yrWRcrdLwst;
    private String yrWRcrdLwstDt;
    private String basPntm;
    private String basIdx;

    // KOSDAQ 데이터로부터 응답 DTO 생성
    public static StockIndexResponseDTO fromKOSDAQ(KOSDAQStockIndexDTO kosdaqDto) {
        return kosdaqDto == null ? null : StockIndexResponseDTO.builder()
                .id(kosdaqDto.getId())
                .basDt(kosdaqDto.getBasDt())
                .idxNm(kosdaqDto.getIdxNm())
                .idxCsf(kosdaqDto.getIdxCsf())
                .epyItmsCnt(kosdaqDto.getEpyItmsCnt())
                .clpr(kosdaqDto.getClpr())
                .vs(kosdaqDto.getVs())
                .fltRt(kosdaqDto.getFltRt())
                .mkp(kosdaqDto.getMkp())
                .hipr(kosdaqDto.getHipr())
                .lopr(kosdaqDto.getLopr())
                .trqu(kosdaqDto.getTrqu())
                .trPrc(kosdaqDto.getTrPrc())
                .lstgMrktTotAmt(kosdaqDto.getLstgMrktTotAmt())
                .lsYrEdVsFltRg(kosdaqDto.getLsYrEdVsFltRg())
                .lsYrEdVsFltRt(kosdaqDto.getLsYrEdVsFltRt())
                .yrWRcrdHgst(kosdaqDto.getYrWRcrdHgst())
                .yrWRcrdHgstDt(kosdaqDto.getYrWRcrdHgstDt())
                .yrWRcrdLwst(kosdaqDto.getYrWRcrdLwst())
                .yrWRcrdLwstDt(kosdaqDto.getYrWRcrdLwstDt())
                .basPntm(kosdaqDto.getBasPntm())
                .basIdx(kosdaqDto.getBasIdx())
                .build();
    }

    // KOSPI 데이터로부터 응답 DTO 생성
    public static StockIndexResponseDTO fromKOSPI(KOSPIStockIndexDTO kospiDto) {
        return kospiDto == null ? null : StockIndexResponseDTO.builder()
                .id(kospiDto.getId())
                .basDt(kospiDto.getBasDt())
                .idxNm(kospiDto.getIdxNm())
                .idxCsf(kospiDto.getIdxCsf())
                .epyItmsCnt(kospiDto.getEpyItmsCnt())
                .clpr(kospiDto.getClpr())
                .vs(kospiDto.getVs())
                .fltRt(kospiDto.getFltRt())
                .mkp(kospiDto.getMkp())
                .hipr(kospiDto.getHipr())
                .lopr(kospiDto.getLopr())
                .trqu(kospiDto.getTrqu())
                .trPrc(kospiDto.getTrPrc())
                .lstgMrktTotAmt(kospiDto.getLstgMrktTotAmt())
                .lsYrEdVsFltRg(kospiDto.getLsYrEdVsFltRg())
                .lsYrEdVsFltRt(kospiDto.getLsYrEdVsFltRt())
                .yrWRcrdHgst(kospiDto.getYrWRcrdHgst())
                .yrWRcrdHgstDt(kospiDto.getYrWRcrdHgstDt())
                .yrWRcrdLwst(kospiDto.getYrWRcrdLwst())
                .yrWRcrdLwstDt(kospiDto.getYrWRcrdLwstDt())
                .basPntm(kospiDto.getBasPntm())
                .basIdx(kospiDto.getBasIdx())
                .build();
    }
}