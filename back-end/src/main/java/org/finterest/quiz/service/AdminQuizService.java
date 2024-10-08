package org.finterest.quiz.service;

import org.finterest.quiz.dao.AdminQuizDAO;
import org.finterest.quiz.domain.vo.QuizVO;
import org.finterest.quiz.mapper.AdminQuizMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminQuizService {
    private AdminQuizDAO adminQuizDAO;

    @Autowired
    public void setAdminQuizDAO(AdminQuizDAO adminQuizDAO) {
        this.adminQuizDAO = adminQuizDAO;
    }

    // 4 퀴즈 세트 삭제
    @Transactional
    public void deleteQuizSet(int setId){
        adminQuizDAO.deleteUserAnswersBySetId(setId);
        adminQuizDAO.deleteQuizSetResultsBySetId(setId);
        adminQuizDAO.deleteQuizSetQuestionsBySetId(setId);
        adminQuizDAO.deleteQuizSetBySetId(setId);
    }

    // 퀴즈 생성
    public void createQuiz(QuizVO quizVO) {
        adminQuizDAO.insertQuiz(quizVO);
    }

    // 특정 퀴즈 조회
    public QuizVO getQuizById(int quizId) {
        return adminQuizDAO.selectQuizById(quizId);
    }

    // 모든 퀴즈 조회
    public List<QuizVO> getAllQuizzes() {
        return adminQuizDAO.selectAllQuizzes();
    }

    // 카테고리 ID별 퀴즈 조회
    public List<QuizVO> getQuizzesByCategoryId(int categoryId) {
        return adminQuizDAO.selectQuizzesByCategoryId(categoryId);
    }

    // 퀴즈 업데이트
    public void updateQuiz(QuizVO quizVO) {
        adminQuizDAO.updateQuiz(quizVO);
    }

    // 퀴즈 삭제
    @Transactional
    public void deleteQuiz(int quizId) {
        adminQuizDAO.deleteUserAnswersByQuizId(quizId);
//        adminQuizDAO.deleteQuizSetResultsByQuizId(quizId);
        adminQuizDAO.deleteQuizSetQuestionsByQuizId(quizId);
//        adminQuizDAO.deleteQuizSetByQuizId(quizId);
        adminQuizDAO.deleteQuiz(quizId);
    }

}
