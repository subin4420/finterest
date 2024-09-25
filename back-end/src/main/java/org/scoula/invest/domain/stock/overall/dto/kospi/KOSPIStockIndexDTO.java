package org.scoula.invest.domain.stock.overall.dto.kospi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.scoula.invest.domain.stock.overall.domain.kospi.KOSPIStockIndexVO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KOSPIStockIndexDTO {
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

    // VO -> DTO 변환
    public static KOSPIStockIndexDTO of(KOSPIStockIndexVO vo) {
        return vo == null ? null : KOSPIStockIndexDTO.builder()
                .id(vo.getId())
                .basDt(vo.getBasDt())
                .idxNm(vo.getIdxNm())
                .idxCsf(vo.getIdxCsf())
                .epyItmsCnt(vo.getEpyItmsCnt())
                .clpr(vo.getClpr())
                .vs(vo.getVs())
                .fltRt(vo.getFltRt())
                .mkp(vo.getMkp())
                .hipr(vo.getHipr())
                .lopr(vo.getLopr())
                .trqu(vo.getTrqu())
                .trPrc(vo.getTrPrc())
                .lstgMrktTotAmt(vo.getLstgMrktTotAmt())
                .lsYrEdVsFltRg(vo.getLsYrEdVsFltRg())
                .lsYrEdVsFltRt(vo.getLsYrEdVsFltRt())
                .yrWRcrdHgst(vo.getYrWRcrdHgst())
                .yrWRcrdHgstDt(vo.getYrWRcrdHgstDt())
                .yrWRcrdLwst(vo.getYrWRcrdLwst())
                .yrWRcrdLwstDt(vo.getYrWRcrdLwstDt())
                .basPntm(vo.getBasPntm())
                .basIdx(vo.getBasIdx())
                .build();
    }

    // DTO -> VO 변환
    public KOSPIStockIndexVO toVo() {
        return KOSPIStockIndexVO.builder()
                .id(id)
                .basDt(basDt)
                .idxNm(idxNm)
                .idxCsf(idxCsf)
                .epyItmsCnt(epyItmsCnt)
                .clpr(clpr)
                .vs(vs)
                .fltRt(fltRt)
                .mkp(mkp)
                .hipr(hipr)
                .lopr(lopr)
                .trqu(trqu)
                .trPrc(trPrc)
                .lstgMrktTotAmt(lstgMrktTotAmt)
                .lsYrEdVsFltRg(lsYrEdVsFltRg)
                .lsYrEdVsFltRt(lsYrEdVsFltRt)
                .yrWRcrdHgst(yrWRcrdHgst)
                .yrWRcrdHgstDt(yrWRcrdHgstDt)
                .yrWRcrdLwst(yrWRcrdLwst)
                .yrWRcrdLwstDt(yrWRcrdLwstDt)
                .basPntm(basPntm)
                .basIdx(basIdx)
                .build();
    }
}