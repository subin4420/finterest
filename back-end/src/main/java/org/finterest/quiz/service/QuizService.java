package org.finterest.quiz.service;

import org.finterest.quiz.dao.QuizDAO;
import org.finterest.quiz.domain.QuizVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {
    private final QuizDAO quizDAO;

    @Autowired
    public QuizService(QuizDAO quizDAO) {
        this.quizDAO = quizDAO;
    }

    public List<QuizVO> quizSetsAll(){
        return quizDAO.quizSetsAll();
    }
}
