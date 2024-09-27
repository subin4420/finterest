package org.finterest.invest.stock.overall.service;

import org.finterest.invest.stock.overall.dto.kospi.KOSPIStockIndexDTO;
import org.finterest.invest.stock.overall.dto.kospi.KOSPIStockListDTO;

import java.util.List;

public interface OverallService {


    // 데이터 조회 관련 메소드
    public List<KOSPIStockIndexDTO> getAllKOSPIStockIndices();
    public List<KOSPIStockListDTO> getAllKOSPIStockLists();

    // 주기적 데이터 갱신 관련 메소드
//    public void deleteKOSPIStockList();
//    public void deleteKOSPIStockIndex(); // 추가

    // 데이터 저장 관련 메소드
    String fetchAndSaveKOSPIStockIndex();
    String fetchAndSaveKOSPIStockList();


    void loadData();

}





