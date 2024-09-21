package org.finterest.quiz.service;

import org.finterest.quiz.dao.QuizSetDetailsDAO;
import org.finterest.quiz.domain.QuizSetDetailsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizSetDetailsService {
    private final QuizSetDetailsDAO quizSetDetailsDAO;

    @Autowired
    public QuizSetDetailsService(QuizSetDetailsDAO quizSetDetailsDAO) {
        this.quizSetDetailsDAO = quizSetDetailsDAO;
    }

    // 특정 퀴즈 세트의 문제 목록을 가져오는 메서드
    public QuizSetDetailsVO getQuizSetDetails(int setId) {
        return quizSetDetailsDAO.selectQuizSetDetails(setId);
    }
}
