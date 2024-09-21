package org.finterest.quiz.service;

import org.finterest.quiz.dao.QuizSetsDAO;
import org.finterest.quiz.domain.QuizSetsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<QuizSetsVO> selectQuizSetsByCategory(int categoryId){
        return quizSetsDAO.selectQuizSetsByCategory(categoryId);
    }

    public QuizSetsVO selectQuizSetById(int id){
        return quizSetsDAO.selectQuizSetById(id);
    }
}
