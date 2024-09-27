package org.finterest.invest.conversion.service;


import org.finterest.invest.conversion.dto.ConversionTransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class ConversionService {

    @Autowired
    private ConversionService conversionService;


    //과정에 대한 비지니스 로직
    //트리거로 자동 반영하기때문에 따로 usermapper의 기능을 추가하거나 가져올 필요는 없음.

    @Transactional
    public void processConversion(int userId, BigDecimal moneyChange, int pointChange, String conversionType) {
        //1. 사용자 id 가져오기



        //

    }



}