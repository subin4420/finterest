package org.finterest.quiz.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.finterest.quiz.domain.QuizSetDetailsVO;

@Mapper
public interface QuizSetDetailsMapper {

    // 특정 퀴즈 세트의 문제 목록 조회
    QuizSetDetailsVO selectQuizSetDetails(@Param("setId") int setId);

}
