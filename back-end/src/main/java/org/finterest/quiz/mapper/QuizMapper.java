package org.finterest.quiz.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.finterest.quiz.domain.QuizVO;

import java.util.List;

@Mapper
public interface QuizMapper {
    List<QuizVO> quizSetsAll();
}
