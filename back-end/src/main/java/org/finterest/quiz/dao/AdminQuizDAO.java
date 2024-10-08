package org.finterest.quiz.dao;

import org.apache.ibatis.annotations.Param;
import org.finterest.quiz.domain.vo.QuizVO;
import org.finterest.quiz.mapper.AdminQuizMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdminQuizDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    public AdminQuizDAO(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    // 4. 퀴즈 세트 삭제
    public void deleteQuizSetResultsBySetId(int setId){
        sqlSessionTemplate.getMapper(AdminQuizMapper.class).deleteQuizSetResultsBySetId(setId);
    }
    public void deleteUserAnswersBySetId(int setId){
        sqlSessionTemplate.getMapper(AdminQuizMapper.class).deleteUserAnswersBySetId(setId);
    }
    public void deleteQuizSetQuestionsBySetId(int setId){
        sqlSessionTemplate.getMapper(AdminQuizMapper.class).deleteQuizSetQuestionsBySetId(setId);
    }
    public void deleteQuizSetBySetId(int setId){
        sqlSessionTemplate.getMapper(AdminQuizMapper.class).deleteQuizSetBySetId(setId);
    }

    // 퀴즈 생성
    public void insertQuiz(QuizVO quizVO) {
        sqlSessionTemplate.getMapper(AdminQuizMapper.class).insertQuiz(quizVO);
    }

    // 특정 퀴즈 조회
    public QuizVO selectQuizById(int quizId) {
        return sqlSessionTemplate.getMapper(AdminQuizMapper.class).selectQuizById(quizId);
    }

    // 모든 퀴즈 조회
    public List<QuizVO> selectAllQuizzes() {
        return sqlSessionTemplate.getMapper(AdminQuizMapper.class).selectAllQuizzes();
    }

    // 카테고리 ID로 퀴즈 조회
    public List<QuizVO> selectQuizzesByCategoryId(int categoryId) {
        return sqlSessionTemplate.getMapper(AdminQuizMapper.class).selectQuizzesByCategoryId(categoryId);
    }

    // 퀴즈 업데이트
    public void updateQuiz(QuizVO quizVO) {
        sqlSessionTemplate.getMapper(AdminQuizMapper.class).updateQuiz(quizVO);
    }

    // 퀴즈 삭제
    public void deleteUserAnswersByQuizId(int quizId){
        sqlSessionTemplate.getMapper(AdminQuizMapper.class).deleteUserAnswersByQuizId(quizId);
    }

    public void deleteQuizSetResultsByQuizId(int quizId){
        sqlSessionTemplate.getMapper(AdminQuizMapper.class).deleteQuizSetResultsByQuizId(quizId);
    }

    public void deleteQuizSetQuestionsByQuizId(int quizId){
        sqlSessionTemplate.getMapper(AdminQuizMapper.class).deleteQuizSetQuestionsByQuizId(quizId);
    }

    public void deleteQuizSetByQuizId(int quizId){
        sqlSessionTemplate.getMapper(AdminQuizMapper.class).deleteQuizSetByQuizId(quizId);
    }

    public void deleteQuiz(int quizId) {
        sqlSessionTemplate.getMapper(AdminQuizMapper.class).deleteQuiz(quizId);
    }
}
