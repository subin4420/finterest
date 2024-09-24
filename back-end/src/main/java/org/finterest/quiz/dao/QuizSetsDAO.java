package org.finterest.quiz.dao;

import org.finterest.quiz.domain.vo.QuizResultVO;
import org.finterest.quiz.domain.vo.QuizSetsVO;
import org.finterest.quiz.domain.vo.QuizVO;
import org.finterest.quiz.domain.vo.UserAnswerVO;
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

    public List<QuizResultVO> selectQuizResultsByUserId(int userId){
        return sqlSessionTemplate.getMapper(QuizSetsMapper.class).selectQuizResultsByUserId(userId);
    }

    public List<QuizSetsVO> selectQuizSetsByCategory(int categoryId){
        return sqlSessionTemplate.getMapper(QuizSetsMapper.class).selectQuizSetsByCategory(categoryId);
    }

    public QuizSetsVO selectQuizSetById(int id){
        return sqlSessionTemplate.getMapper(QuizSetsMapper.class).selectQuizSetById(id);
    }

    public List<QuizVO> selectQuestionsBySetId(int setId){
        return sqlSessionTemplate.getMapper(QuizSetsMapper.class).selectQuestionsBySetId(setId);
    }

    public QuizVO selectQuizById(int quizId){
        return sqlSessionTemplate.getMapper(QuizSetsMapper.class).selectQuizById(quizId);
    }

    public int selectCorrectChoiceByQuizId(int quizId){
        return sqlSessionTemplate.getMapper(QuizSetsMapper.class).selectCorrectChoiceByQuizId(quizId);
    }

    public int insertQuizResult(int userId, int setId, int totalScore, int maxScore) {
        // MyBatis가 생성된 result_id를 keyProperty로 반환하도록 설정
        sqlSessionTemplate.getMapper(QuizSetsMapper.class).insertQuizResult(userId, setId, totalScore, maxScore);

        // 방금 삽입된 결과의 resultId를 반환
        return sqlSessionTemplate.getMapper(QuizSetsMapper.class).getLastInsertedResultId();
    }


    public void insertUserAnswer(int resultId, int quizId, int userId, int userAnswer, boolean isCorrect){
        sqlSessionTemplate.getMapper(QuizSetsMapper.class).insertUserAnswer(resultId, quizId, userId, userAnswer, isCorrect);
    }

    public int selectPointsForQuiz() {
        return sqlSessionTemplate.getMapper(QuizSetsMapper.class).selectPointsForQuiz();
    }

    public QuizResultVO getQuizResult(int userId, int setId) {
        return sqlSessionTemplate.getMapper(QuizSetsMapper.class).selectQuizResultByUserAndSet(userId, setId);
    }

    public List<UserAnswerVO> selectUserAnswers(int resultId, int userId){
        return sqlSessionTemplate.getMapper(QuizSetsMapper.class).selectUserAnswers(resultId, userId);
    }
}
