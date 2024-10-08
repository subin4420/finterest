package org.finterest.quiz.service;

import org.finterest.quiz.dao.QuizSetsDAO;
import org.finterest.quiz.domain.dto.QuizSubmissionDTO;
import org.finterest.quiz.domain.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuizSetsService {
    private final QuizSetsDAO quizSetsDAO;

    @Autowired
    public QuizSetsService(QuizSetsDAO quizSetsDAO) {
        this.quizSetsDAO = quizSetsDAO;
    }

    public List<QuizSetsVO> selectAllQuizSets(){
        return quizSetsDAO.selectAllQuizSets();
    }

    public Map<Integer, Map<String, Object>> getQuizResultsForUser(int userId) {
        List<QuizResultDetailVO> quizResults = quizSetsDAO.selectQuizResultsByUserId(userId);
        Map<Integer, Map<String, Object>> resultsMap = new HashMap<>();

        for (QuizResultDetailVO result : quizResults) {
            Map<String, Object> resultData = new HashMap<>();
            resultData.put("score", result.getTotalScore());  // 점수 맵핑
            resultData.put("completedAt", result.getCompletedAt());  // 완료 날짜 맵핑
            resultsMap.put(result.getSetId(), resultData);
        }

        return resultsMap;
    }



    public List<QuizSetsVO> selectQuizSetsByCategory(int categoryId){
        return quizSetsDAO.selectQuizSetsByCategory(categoryId);
    }

    public QuizSetsVO selectQuizSetById(int id){
        return quizSetsDAO.selectQuizSetById(id);
    }

    public List<QuizVO> selectQuestionsBySetId(int setId){
        return quizSetsDAO.selectQuestionsBySetId(setId);
    }

    public QuizVO selectQuizById(int quizId){
        return quizSetsDAO.selectQuizById(quizId);
    }

    public int selectCorrectChoiceByQuizId(int quizId){
        return quizSetsDAO.selectCorrectChoiceByQuizId(quizId);
    }



    // 퀴즈 제출 처리
    public QuizResultDetailVO submitQuiz(int setId, int userId, QuizSubmissionDTO submission) {
        // "퀴즈"에 해당하는 points_awarded 값 가져오기
        int pointsPerQuestion = quizSetsDAO.selectPointsForQuiz();  // 각 문제당 포인트

        int totalScore = 0;
        int maxScore = submission.getAnswers().size() * pointsPerQuestion;  // 각 문제는 해당 포인트만큼 점수
        List<QuizResultDetailVO.CorrectAnswer> correctAnswers = new ArrayList<>();
        List<QuizResultDetailVO.WrongAnswer> wrongAnswers = new ArrayList<>();

        // 제출된 답변을 순회하며 정답 여부 확인 및 점수 계산
        for (QuizSubmissionDTO.AnswerDTO answer : submission.getAnswers()) {
            int correctChoice = quizSetsDAO.selectCorrectChoiceByQuizId(answer.getQuizId());

            // 정답 여부 확인 및 점수 계산
            if (correctChoice == answer.getSelectedChoice()) {
                totalScore += pointsPerQuestion;
                correctAnswers.add(new QuizResultDetailVO.CorrectAnswer(answer.getQuizId(), correctChoice));
            } else {
                wrongAnswers.add(new QuizResultDetailVO.WrongAnswer(answer.getQuizId(), answer.getSelectedChoice()));
            }
        }

        // 퀴즈 결과를 데이터베이스에 저장하고 resultId 반환
        int resultId = quizSetsDAO.insertQuizResult(userId, setId, totalScore, maxScore);

        // 포인트 이력에 추가
        quizSetsDAO.insertPoint(userId, totalScore* quizSetsDAO.selectPointsForQuiz());

        // 각 사용자의 답변을 resultId와 함께 저장
        for (QuizSubmissionDTO.AnswerDTO answer : submission.getAnswers()) {
            quizSetsDAO.insertUserAnswer(resultId, answer.getQuizId(), userId,
                    answer.getSelectedChoice(), answer.getSelectedChoice() == quizSetsDAO.selectCorrectChoiceByQuizId(answer.getQuizId()));
        }

        // 퀴즈 결과 응답 생성
        QuizResultDetailVO result = new QuizResultDetailVO();
        result.setResultId(resultId);  // 새로 생성된 resultId를 설정
        result.setSetId(setId);  // 퀴즈 세트 ID 설정
        result.setUserId(userId);  // JWT에서 추출된 userId 설정
        result.setTotalScore(totalScore);
        result.setMaxScore(maxScore);
        result.setCorrectAnswers(correctAnswers);
        result.setWrongAnswers(wrongAnswers);

        return result;
    }



    public List<QuizResultVO> getQuizResult(int userId) {
        // 퀴즈 결과 조회
      return quizSetsDAO.getQuizResult(userId);

    }



    public List<UserAnswerVO> getUserAnswers(int resultId, int userId) {
        return quizSetsDAO.selectUserAnswers(resultId, userId);
    }

}
