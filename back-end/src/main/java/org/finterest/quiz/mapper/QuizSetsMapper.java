package org.finterest.quiz.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.finterest.quiz.domain.QuizSetsVO;

import java.util.List;

@Mapper
public interface QuizSetsMapper {
    List<QuizSetsVO> quizSetsAll();
}
