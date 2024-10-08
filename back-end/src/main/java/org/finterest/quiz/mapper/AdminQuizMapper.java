package org.finterest.quiz.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.finterest.quiz.domain.vo.QuizVO;

import java.util.List;

@Mapper
public interface AdminQuizMapper {

    // 4. 퀴즈 세트 삭제
    void deleteQuizSetResultsBySetId(@Param("setId") int setId);
    void deleteUserAnswersBySetId(@Param("setId") int setId);
    void deleteQuizSetQuestionsBySetId(@Param("setId") int setId);
    void deleteQuizSetBySetId(@Param("setId") int setId);

    // 6. 퀴즈 생성
    void insertQuiz(QuizVO quizVO);

    // 특정 퀴즈 조회
    QuizVO selectQuizById(int quizId);

    // 5. 모든 퀴즈 조회
    List<QuizVO> selectAllQuizzes();

    // 5-2. 카테고리 ID로 퀴즈 조회
    List<QuizVO> selectQuizzesByCategoryId(int categoryId);

    // 7. 퀴즈 업데이트
    void updateQuiz(QuizVO quizVO);

    // 8. 퀴즈 삭제
    // 8-1. User_Answers 테이블에서 quiz_id로 삭제
    void deleteUserAnswersByQuizId(@Param("quizId") int quizId);

    // 8-2. Quiz_Sets_Results 테이블에서 quiz_id로 삭제
    void deleteQuizSetResultsByQuizId(@Param("quizId") int quizId);

    // 8-3. Quiz_Set_Questions 테이블에서 quiz_id로 삭제
    void deleteQuizSetQuestionsByQuizId(@Param("quizId") int quizId);

    // 8-4. Quiz_Sets 테이블에서 quiz_id로 삭제
    void deleteQuizSetByQuizId(@Param("quizId") int quizId);

    // 8-5. Quizzes 테이블에서 quiz_id로 삭제
    void deleteQuiz(@Param("quizId") int quizId);
}
