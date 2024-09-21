package org.finterest.quiz.service;

import org.finterest.quiz.dao.QuizSetsDAO;
import org.finterest.quiz.domain.QuizSetsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizSetsService {
    private final QuizSetsDAO quizDAO;

    @Autowired
    public QuizSetsService(QuizSetsDAO quizDAO) {
        this.quizDAO = quizDAO;
    }

    public List<QuizSetsVO> quizSetsAll(){
        return quizDAO.quizSetsAll();
    }
}
