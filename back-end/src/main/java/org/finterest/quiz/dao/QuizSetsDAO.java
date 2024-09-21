package org.finterest.quiz.dao;

import org.finterest.quiz.domain.QuizSetsVO;
import org.finterest.quiz.mapper.QuizSetsMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuizSetsDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    public QuizSetsDAO(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    public List<QuizSetsVO> selectAllQuizSets(){
        return sqlSessionTemplate.getMapper(QuizSetsMapper.class).selectAllQuizSets();
    }

    public List<QuizSetsVO> selectQuizSetsByCategory(int categoryId){
        return sqlSessionTemplate.getMapper(QuizSetsMapper.class).selectQuizSetsByCategory(categoryId);
    }

    public QuizSetsVO selectQuizSetById(int id){
        return sqlSessionTemplate.getMapper(QuizSetsMapper.class).selectQuizSetById(id);
    }
}
