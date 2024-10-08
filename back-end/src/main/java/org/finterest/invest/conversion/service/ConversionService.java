package org.finterest.invest.conversion.service;

import org.finterest.invest.conversion.dto.ConversionRateDTO;
import org.finterest.invest.conversion.dto.ConversionTransactionDTO;
import org.finterest.invest.conversion.mapper.ConversionMapper;
import org.finterest.security.account.domain.UserVO;
import org.finterest.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConversionService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ConversionMapper conversionMapper;

    /**
     * 포인트를 가상 자금으로 환전하는 메서드
     *
     * @param username    사용자 이름
     * @param pointAmount 환전할 포인트 양
     * @return 환전 성공 여부 메시지
     */
    public String convertPointsToMoney(String username, int pointAmount) {
        // 사용자 정보 조회
        UserVO user = userMapper.get(username);
        if (user == null) {
            return "사용자를 찾을 수 없습니다.";
        }

        // 포인트가 충분한지 확인
        if (user.getTotalPoints() < pointAmount) {
            return "포인트가 충분하지 않습니다.";
        }

        // 최신 환전 비율 조회
        ConversionRateDTO rate = conversionMapper.getLatestRate();
        if (rate == null) {
            return "환전 비율을 조회할 수 없습니다.";
        }

        // 환전 비율을 사용하여 가상 자금 계산
        BigDecimal moneyChange = rate.getConversionRate().multiply(BigDecimal.valueOf(pointAmount));

        // Conversion_Transactions 테이블에 환전 내역 삽입
        ConversionTransactionDTO transaction = ConversionTransactionDTO.builder()
                .userId(user.getUserId())
                .moneyChange(moneyChange)
                .pointChange(-pointAmount) // 포인트는 감소
                .conversionType("points_to_money")
                .rateId(rate.getRateId())
                .build();

        conversionMapper.insertConversionTransaction(transaction.toVo()); // DTO를 VO로 변환하여 전달

        return "포인트를 가상 자금으로 환전했습니다.";
    }

    /**
     * 가상 자금을 포인트로 환전하는 메서드
     *
     * @param username   사용자 이름
     * @param moneyAmount 환전할 가상 자금 양
     * @return 환전 성공 여부 메시지
     */
    public String convertMoneyToPoints(String username, BigDecimal moneyAmount) {
        // 사용자 정보 조회
        UserVO user = userMapper.get(username);
        if (user == null) {
            return "사용자를 찾을 수 없습니다.";
        }

        // 가상 자금이 충분한지 확인
        if (user.getMoney().compareTo(moneyAmount) < 0) {
            return "가상 자금이 충분하지 않습니다.";
        }

        // 최신 환전 비율 조회
        ConversionRateDTO rate = conversionMapper.getLatestRate();
        if (rate == null) {
            return "환전 비율을 조회할 수 없습니다.";
        }

        // 환전 비율을 사용하여 포인트 계산
        int pointChange = moneyAmount.divide(rate.getConversionRate(), BigDecimal.ROUND_DOWN).intValue();

        // Conversion_Transactions 테이블에 환전 내역 삽입
        ConversionTransactionDTO transaction = ConversionTransactionDTO.builder()
                .userId(user.getUserId())
                .moneyChange(moneyAmount.negate()) // 돈은 감소
                .pointChange(pointChange) // 포인트는 증가
                .conversionType("money_to_points")
                .rateId(rate.getRateId())
                .build();

        conversionMapper.insertConversionTransaction(transaction.toVo()); // DTO를 VO로 변환하여 전달

        return "가상 자금을 포인트로 환전했습니다.";
    }

    /**
     * 특정 사용자의 환전 거래 내역을 조회하는 메서드
     *
     * @param username 사용자 이름
     * @return 환전 거래 내역 리스트
     */
    public List<ConversionTransactionDTO> getConversionTransactions(String username) {
        // 사용자 정보 조회
        UserVO user = userMapper.get(username);
        if (user == null) {
            return Collections.emptyList(); // 사용자 없음
        }

        // 사용자 ID로 환전 거래 내역 조회
        List<ConversionTransactionDTO> transactions = conversionMapper.getConversionTransactionsByUserId(user.getUserId())
                .stream()
                .map(ConversionTransactionDTO::of) // VO를 DTO로 변환
                .collect(Collectors.toList());

        return transactions;
    }


    public ConversionRateDTO getLatestConversionRate() {
        ConversionRateDTO rate = conversionMapper.getLatestRate();
        if (rate == null) {
            throw new RuntimeException("환전 비율을 조회할 수 없습니다.");
        }
        return rate;
    }

    public void updateConversionRate(ConversionRateDTO rateDTO) {
        conversionMapper.updateConversionRate(rateDTO);
    }

    //
    public List<ConversionRateDTO> getAllConversionRates() {
        return conversionMapper.getAllRates();
    }
}
