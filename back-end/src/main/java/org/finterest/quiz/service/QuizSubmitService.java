package org.finterest.quiz.service;

import org.apache.ibatis.annotations.Param;
import org.finterest.quiz.dao.QuizSubmitDAO;
import org.finterest.quiz.domain.QuizVO;
import org.finterest.quiz.dto.AnswerSubmissionDTO;
import org.finterest.quiz.dto.CorrectAnswerDTO;
import org.finterest.quiz.dto.QuizResultDTO;
import org.finterest.quiz.dto.WrongAnswerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizSubmitService {
    private final QuizSubmitDAO quizSubmitDAO;

    @Autowired
    public QuizSubmitService(QuizSubmitDAO quizSubmitDAO) {
        this.quizSubmitDAO = quizSubmitDAO;
    }

    public QuizResultDTO submitQuizSet(int setId, int userId, List<AnswerSubmissionDTO> answers) {
        int totalScore = 0;
        int maxScore = quizSubmitDAO.getMaxScoreForQuizSet(setId);
        List<CorrectAnswerDTO> correctAnswers = new ArrayList<>();
        List<WrongAnswerDTO> wrongAnswers = new ArrayList<>();

        for (AnswerSubmissionDTO answer : answers) {
            // 디버그 로그 추가: quizId와 selectedChoice 확인
            System.out.println("Quiz ID: " + answer.getQuizId());
            System.out.println("Selected Choice: " + answer.getSelectedChoice());

            QuizVO quiz = quizSubmitDAO.getQuizById(answer.getQuizId());

            if (quiz == null) {
                throw new IllegalArgumentException("Quiz with ID " + answer.getQuizId() + " not found.");
            }

            boolean isCorrect = (quiz.getCorrectChoice() == answer.getSelectedChoice());
            if (isCorrect) {
                totalScore += 10;
                correctAnswers.add(new CorrectAnswerDTO(quiz.getQuizId(), quiz.getCorrectChoice()));
            } else {
                wrongAnswers.add(new WrongAnswerDTO(quiz.getQuizId(), quiz.getCorrectChoice()));
            }

            quizSubmitDAO.saveUserAnswer(0, answer.getQuizId(), userId, answer.getSelectedChoice(), isCorrect);
        }

        int resultId = quizSubmitDAO.saveQuizResult(userId, setId, totalScore, maxScore);

        return new QuizResultDTO(resultId, totalScore, maxScore, correctAnswers, wrongAnswers);
    }
}

