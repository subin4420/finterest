package org.finterest.invest.conversion.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.finterest.invest.conversion.domain.ConversionRateVO;
import org.finterest.invest.conversion.domain.ConversionTransactionVO;
import org.finterest.invest.conversion.dto.ConversionRateDTO;
import org.finterest.invest.conversion.dto.ConversionTransactionDTO;

import java.util.List;

@Mapper
public interface ConversionMapper {

// 조회, 비율 조정 관련 mapper
    public ConversionRateDTO getLatestRate(String conversionType); //최신 환전 비율

    public void insertConversionRate(ConversionRateDTO rateDTO); // 환전비율 입력

    public void updateConversionRate(ConversionRateDTO rateDTO); // 환전비율 업데이트

    public List<ConversionRateDTO> getAllRates(); //환전비율 기록 조회


// 트랜잭션 추가, 사용자별 거래 내역 조회

    public void insertConversionTransaction(ConversionTransactionDTO transactionDTO); //환전내역 저장


    List<ConversionTransactionVO> selectTransactionsByUserId(@Param("userId") int userId); // 사용자 ID로 거래내역 조회
}
