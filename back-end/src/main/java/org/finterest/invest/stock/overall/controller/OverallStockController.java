package org.finterest.invest.stock.overall.controller;

import lombok.extern.log4j.Log4j;
import org.finterest.invest.stock.overall.dto.kospi.KOSPIStockIndexDTO;
import org.finterest.invest.stock.overall.dto.kospi.KOSPIStockListDTO;
import org.finterest.invest.stock.overall.service.OverallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j
@RestController
@RequestMapping("/stock")
public class OverallStockController {

    private final OverallService overallService;

    @Autowired
    public OverallStockController(OverallService overallService) {
        this.overallService = overallService;
    }

    @GetMapping("/list/KOSPI")
    public ResponseEntity<List<KOSPIStockListDTO>> getListOfKOSPI() {
        log.info("Request received for KOSPI stock list");
        List<KOSPIStockListDTO> kospiStockList = overallService.getAllKOSPIStockLists();
        return new ResponseEntity<>(kospiStockList, HttpStatus.OK);
    }


    @GetMapping("/index/KOSPI")
    public ResponseEntity<List<KOSPIStockIndexDTO>> getIndexOfKOSPI() {
        log.info("Request received for KOSPI stock index");
        List<KOSPIStockIndexDTO> kospiStockIndices = overallService.getAllKOSPIStockIndices();
        return new ResponseEntity<>(kospiStockIndices, HttpStatus.OK);
    }

}
