package org.finterest.quiz.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.finterest.quiz.domain.QuizVO;

@Mapper
public interface QuizSubmitMapper {
    int getMaxScoreForQuizSet(int setId);

    QuizVO getQuizById(int quizId);

    void saveUserAnswer(int resultId, int quizId,
                        int userId, int selectedChoice,
                        boolean isCorrect);

    int saveQuizResult(int userId, int setId,
                       int totalScore, int maxScore);
}
