package org.finterest.quiz.dao;

import org.finterest.quiz.domain.QuizVO;
import org.finterest.quiz.mapper.QuizMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuizDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    public QuizDAO(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    public List<QuizVO> quizSetsAll(){
        return sqlSessionTemplate.getMapper(QuizMapper.class).quizSetsAll();
    }
}
