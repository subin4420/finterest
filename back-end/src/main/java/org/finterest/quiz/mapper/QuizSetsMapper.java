package org.finterest.quiz.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.finterest.quiz.domain.vo.*;

import java.util.List;

@Mapper
public interface QuizSetsMapper {
    // 1. 전체 퀴즈 세트 목록 조회
    List<QuizSetsVO> selectAllQuizSets();

    // 1-1. 사용자가 푼 퀴즈 결과 조회
    List<QuizResultDetailVO> selectQuizResultsByUserId(int userId);

    // 1-2. 카테고리별 퀴즈 세트 목록 조회
    List<QuizSetsVO> selectQuizSetsByCategory(int categoryId);

    // 2. 특정 퀴즈 세트 조회 (세트 ID 기준)
    QuizSetsVO selectQuizSetById(int setId);

    // 2-1. 특정 퀴즈 세트에 속한 문제 목록 조회 (세트 ID 기준)
    List<QuizVO> selectQuestionsBySetId(int setId);

    // 3. 특정 퀴즈 문제 조회 (퀴즈 ID 기준)
    QuizVO selectQuizById(@Param("quizId") int quizId);

    // 4. 특정 퀴즈의 정답을 가져옴
    int selectCorrectChoiceByQuizId(@Param("quizId") int quizId);

    // 4. 퀴즈 결과 저장
    int insertQuizResult(@Param("userId") int userId, @Param("setId") int setId,
                          @Param("totalScore") int totalScore, @Param("maxScore") int maxScore);

    int getLastInsertedResultId();

    // 4. 사용자 답변 저장
    void insertUserAnswer(@Param("resultId") int resultId, @Param("quizId") int quizId,
                          @Param("userId") int userId, @Param("userAnswer") int userAnswer,
                          @Param("isCorrect") boolean isCorrect);

    // 퀴즈 포인트 조회
    int selectPointsForQuiz();

    /// 5. 사용자별 퀴즈 결과 조회
    List<QuizResultVO> selectQuizResultsByUser(@Param("userId") int userId);

    // 특정 resultId와 userId에 대한 사용자 답변 목록 조회
    List<UserAnswerVO> selectUserAnswers(@Param("resultId") int resultId, @Param("userId") int userId);

}
