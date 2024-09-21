package org.finterest.quiz.dao;

import org.finterest.quiz.domain.QuizSetDetailsVO;
import org.finterest.quiz.mapper.QuizSetDetailsMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class QuizSetDetailsDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    public QuizSetDetailsDAO(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    // 특정 퀴즈 세트의 문제 목록 조회
    public QuizSetDetailsVO selectQuizSetDetails(int setId) {
        return sqlSessionTemplate.getMapper(QuizSetDetailsMapper.class).selectQuizSetDetails(setId);
    }
}
