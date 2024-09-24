package org.finterest.invest.conversion.service;

import org.finterest.invest.conversion.domain.ConversionRateVO;
import org.finterest.invest.conversion.domain.ConversionTransactionVO;
import org.finterest.invest.conversion.dto.ConversionTransactionDTO;
import org.finterest.invest.conversion.mapper.ConversionMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ConversionService {
    private final ConversionMapper conversionMapper;

    public ConversionService(ConversionMapper conversionMapper) {
        this.conversionMapper = conversionMapper;
    }

    // 포인트를 가상 돈으로 환전하는 메서드
    public ConversionTransactionDTO convertPointsToMoney(int userId, int points) {
        BigDecimal rate = getCurrentConversionRate();
        BigDecimal moneyReceived = BigDecimal.valueOf(points).multiply(rate);

        // 거래 정보 저장
        ConversionTransactionVO transaction = new ConversionTransactionVO();
        transaction.setUserId(userId);
        transaction.setPointsUsed(points);
        transaction.setMoneyReceived(moneyReceived);
        transaction.setConversionType("points_to_money");

        conversionMapper.insertConversionTransaction(transaction);

        return ConversionTransactionDTO.of(transaction);
    }

    // 가상 돈을 포인트로 환전하는 메서드
    public ConversionTransactionDTO convertMoneyToPoints(int userId, BigDecimal money) {
        BigDecimal rate = getCurrentConversionRate();
        int pointsReceived = money.divide(rate, BigDecimal.ROUND_DOWN).intValue();

        // 거래 정보 저장
        ConversionTransactionVO transaction = new ConversionTransactionVO();
        transaction.setUserId(userId);
        transaction.setMoneyUsed(money);
        transaction.setPointsReceived(pointsReceived);
        transaction.setConversionType("money_to_points");

        conversionMapper.insertConversionTransaction(transaction);

        return ConversionTransactionDTO.of(transaction);
    }

    // 현재 환전 비율을 가져오는 메서드
    public BigDecimal getCurrentConversionRate() {
        ConversionRateVO rate = conversionMapper.selectConversionRateById(1); // ID 1 예시
        return rate.getConversionRate();
    }
}
