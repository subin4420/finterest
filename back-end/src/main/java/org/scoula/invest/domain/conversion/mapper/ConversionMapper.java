package org.scoula.invest.domain.conversion.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.scoula.invest.domain.conversion.domain.ConversionRateVO;
import org.scoula.invest.domain.conversion.domain.ConversionTransactionVO;

import java.util.List;

@Mapper
public interface ConversionMapper {


    void updateConversionRate(ConversionRateVO conversionRate); // 환전 비율 업데이트

    ConversionRateVO selectConversionRateById(@Param("rateId") int rateId); // 특정 환전 비율 조회

    List<ConversionRateVO> selectAllConversionRates(); // 모든 환전 비율 조회







    void insertConversionTransaction(ConversionTransactionVO conversionTransaction); // 거래내역 추가
    List<ConversionTransactionVO> selectTransactionsByUserId(@Param("userId") int userId); // 사용자 ID로 거래내역 조회
}
