package org.finterest.invest.conversion.service;

import org.finterest.invest.conversion.dto.ConversionRateDTO;
import org.finterest.invest.conversion.dto.ConversionTransactionDTO;
import org.finterest.invest.conversion.dto.UserBalanceDTO;
import org.finterest.invest.conversion.mapper.ConversionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.math.RoundingMode;

@Service
public class ConversionService {

    @Autowired
    private ConversionMapper conversionMapper;

    private static final Logger logger = LoggerFactory.getLogger(ConversionService.class);

    
    public String convertPointsToMoney(Integer userId, int pointAmount) {
        ConversionRateDTO rate = conversionMapper.getLatestRate();
        if (rate == null) {
            return "환전 비율을 조회할 수 없습니다.";
        }

        BigDecimal moneyChange = rate.getConversionRate().multiply(BigDecimal.valueOf(pointAmount));

        ConversionTransactionDTO transaction = ConversionTransactionDTO.builder()
                .userId(userId)
                .moneyChange(moneyChange)
                .pointChange(-pointAmount)
                .conversionType("points_to_money")
                .rateId(rate.getRateId())
                .build();

        conversionMapper.insertConversionTransaction(transaction.toVo());

        return "포인트를 가상 자금으로 환전했습니다.";
    }

    /**
     * 가상 자금을 포인트로 환전하는 메서드
     *
     * @param userId   사용자 ID
     * @param moneyAmount 환전할 가상 자금 양
     * @return 환전 성공 여부 메시지
     */
    public String convertMoneyToPoints(Integer userId, BigDecimal moneyAmount) {
        ConversionRateDTO rate = conversionMapper.getLatestRate();
        if (rate == null) {
            return "환전 비율을 조회할 수 없습니다.";
        }

        int pointChange = moneyAmount.divide(rate.getConversionRate(), RoundingMode.DOWN).intValue();

        ConversionTransactionDTO transaction = ConversionTransactionDTO.builder()
                .userId(userId)
                .moneyChange(moneyAmount.negate())
                .pointChange(pointChange)
                .conversionType("money_to_points")
                .rateId(rate.getRateId())
                .build();

        conversionMapper.insertConversionTransaction(transaction.toVo());

        return "가상 자금을 포인트로 환전했습니다.";
    }

    /**
     * 특정 사용자의 환전 거래 내역을 조회하는 메서드
     *
     * @param userId 사용자 ID
     * @return 환전 거래 내역 리스트
     */
    public List<ConversionTransactionDTO> getConversionTransactions(Integer userId) {
        return conversionMapper.getConversionTransactionsByUserId(userId)
                .stream()
                .map(ConversionTransactionDTO::of)
                .collect(Collectors.toList());
    }

    public ConversionRateDTO getLatestConversionRate() {
        try {
            ConversionRateDTO rate = conversionMapper.getLatestRate();
            if (rate == null) {
                logger.error("환전 비율을 조회할 수 없습니다.");
                throw new RuntimeException("환전 비율을 조회할 수 없습니다.");
            }
            return rate;
        } catch (Exception e) {
            logger.error("환전 비율 조회 중 오류 발생", e);
            throw new RuntimeException("환전 비율 조회 중 오류 발생", e);
        }
    }

    public void updateConversionRate(ConversionRateDTO rateDTO) {
        conversionMapper.updateConversionRate(rateDTO);
    }

    //
    public List<ConversionRateDTO> getAllConversionRates() {
        return conversionMapper.getAllRates();
    }

    // getUserBalance 메소드는 제거됨

    // 사용자 잔액 조회 메서드 추가
    public UserBalanceDTO getUserBalance(Integer userId) {
        try {
            UserBalanceDTO balance = conversionMapper.getUserBalance(userId);
            if (balance == null) {
                logger.error("사용자 잔액을 조회할 수 없습니다. 사용자 ID: {}", userId);
                throw new RuntimeException("사용자 잔액을 조회할 수 없습니다.");
            }
            return balance;
        } catch (Exception e) {
            logger.error("사용자 잔액 조회 중 오류 발생. 사용자 ID: {}", userId, e);
            throw new RuntimeException("사용자 잔액 조회 중 오류 발생", e);
        }
    }
}
