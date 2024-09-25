package org.scoula.invest.domain.stock.overall.dto.kospi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.scoula.invest.domain.stock.overall.domain.kospi.KOSPIStockListVO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KOSPIStockListDTO {
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

    // VO -> DTO 변환
    public static KOSPIStockListDTO of(KOSPIStockListVO vo) {
        return vo == null ? null : KOSPIStockListDTO.builder()
                .id(vo.getId())
                .basDt(vo.getBasDt())
                .srtnCd(vo.getSrtnCd())
                .isinCd(vo.getIsinCd())
                .itmsNm(vo.getItmsNm())
                .mrktCtg(vo.getMrktCtg())
                .clpr(vo.getClpr())
                .vs(vo.getVs())
                .fltRt(vo.getFltRt())
                .mkp(vo.getMkp())
                .hipr(vo.getHipr())
                .lopr(vo.getLopr())
                .trqu(vo.getTrqu())
                .trPrc(vo.getTrPrc())
                .lstgStCnt(vo.getLstgStCnt())
                .mrktTotAmt(vo.getMrktTotAmt())
                .build();
    }

    // DTO -> VO 변환
    public KOSPIStockListVO toVo() {
        return KOSPIStockListVO.builder()
                .id(id)
                .basDt(basDt)
                .srtnCd(srtnCd)
                .isinCd(isinCd)
                .itmsNm(itmsNm)
                .mrktCtg(mrktCtg)
                .clpr(clpr)
                .vs(vs)
                .fltRt(fltRt)
                .mkp(mkp)
                .hipr(hipr)
                .lopr(lopr)
                .trqu(trqu)
                .trPrc(trPrc)
                .lstgStCnt(lstgStCnt)
                .mrktTotAmt(mrktTotAmt)
                .build();
    }
}