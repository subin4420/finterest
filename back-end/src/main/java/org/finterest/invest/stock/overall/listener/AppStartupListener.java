package org.finterest.invest.stock.overall.listener;

import org.finterest.invest.stock.overall.service.OverallService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class AppStartupListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(AppStartupListener.class);

    @Autowired
    private OverallService overallService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("Spring 컨텍스트 초기화됨: 데이터 로딩 시작");
        loadData();
        logger.info("데이터 로딩 완료");
    }

    private void loadData() {
        // 데이터 로딩 로직
        overallService.loadData();
        logger.info("KOSPI Stock Index 및 List 데이터 로딩 완료");
    }
}
