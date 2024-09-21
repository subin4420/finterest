package org.finterest.quiz.dao;

import org.apache.ibatis.annotations.Param;
import org.finterest.quiz.domain.QuizVO;
import org.finterest.quiz.domain.QuizVO;
import org.finterest.quiz.mapper.QuizSubmitMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class QuizSubmitDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    public QuizSubmitDAO(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    // 퀴즈 세트의 최대 점수 계산
    public int getMaxScoreForQuizSet(int quizSetId) {
        return sqlSessionTemplate.getMapper(QuizSubmitMapper.class).getMaxScoreForQuizSet(quizSetId);
    }

    // 특정 퀴즈 문제 가져오기
    public QuizVO getQuizById(int quizId) {
        return sqlSessionTemplate.getMapper(QuizSubmitMapper.class).getQuizById(quizId);
    }

    // 사용자 답변 저장
    public void saveUserAnswer(int resultId, int quizId,
                               int userId, int selectedChoice,
                               boolean isCorrect) {
        sqlSessionTemplate.getMapper(QuizSubmitMapper.class)
                .saveUserAnswer(resultId, quizId, userId, selectedChoice, isCorrect);
    }

    // 퀴즈 세트 결과 저장
    public int saveQuizResult(@Param("userId") int userId, @Param("setId") int setId,
                              @Param("totalScore") int totalScore, @Param("maxScore") int maxScore) {
        return sqlSessionTemplate.getMapper(QuizSubmitMapper.class)
                .saveQuizResult(userId, setId, totalScore, maxScore);
    }
}
