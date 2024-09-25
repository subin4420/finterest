package org.scoula.invest.domain.stock.overall.mapper;

import org.scoula.invest.domain.stock.overall.dto.kospi.KOSPIStockIndexDTO;
import org.scoula.invest.domain.stock.overall.dto.kospi.KOSPIStockListDTO;

import java.util.List;

public interface OverallMapper {

    // KOSPI 주식 리스트 저장 메소드
    void insertKOSPIStockList(KOSPIStockListDTO dto);

    // KOSPI 주식 지수 저장 메소드
    void insertKOSPIStockIndex(KOSPIStockIndexDTO dto);

    // 모든 KOSPI 주식 리스트 조회 메소드
    List<KOSPIStockListDTO> selectAllKOSPIStockLists();

    // 모든 KOSPI 주식 지수 조회 메소드
    List<KOSPIStockIndexDTO> selectAllKOSPIStockIndices();
}
