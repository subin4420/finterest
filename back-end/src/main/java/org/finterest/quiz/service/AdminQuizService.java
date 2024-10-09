package org.finterest.quiz.service;

import org.finterest.quiz.dao.AdminQuizDAO;
import org.finterest.quiz.domain.vo.QuizSetsVO;
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

    // 1. 퀴즈 세트 생성
    @Transactional
    public void createQuizSet(QuizSetsVO quizSetsVO, List<QuizVO> quizList) {
        try {

            // 1-2. 퀴즈 세트 생성 (categoryId는 VO에 없으므로 직접 전달)
            //quizSetsVO.setSetId(0);  // 세트 ID를 0으로 설정하고 자동 생성되도록
            adminQuizDAO.insertQuizSet(quizSetsVO);

            // 1-3. 생성된 퀴즈 세트 ID를 가져온 후, 각 퀴즈 문제를 삽입하고 연결
            int setId = quizSetsVO.getSetId();  // 생성된 세트 ID

            for (QuizVO quizVO : quizList) {
                //adminQuizDAO.insertQuiz(quizVO);  // 퀴즈 문제 삽입
                int quizId = quizVO.getQuizId();  // 생성된 퀴즈 문제 ID
                System.out.println(">>>>>>세트 ID: " + setId);
                System.out.println(">>>>>>퀴즈 ID: "+ quizId);
                // 1-4. 퀴즈 세트와 퀴즈 문제 연결
                adminQuizDAO.insertQuizSetQuestions(setId, quizId);
            }
        } catch (Exception e) {
            // 오류 발생 시 처리
            throw new RuntimeException("퀴즈 세트 생성 중 오류가 발생했습니다.", e);
        }
    }


    // 2. 퀴즈 세트 조회
    public List<QuizSetsVO> getAllQuizSets(Integer categoryId){
        return adminQuizDAO.selectQuizSets(categoryId);
    }

    // 3. 퀴즈 세트 수정
    @Transactional
    public void updateQuizSet(QuizSetsVO quizSetsVO, List<QuizVO> quizList) {
        try {
            int setId = quizSetsVO.getSetId();

            // 기존 퀴즈 세트와 관련된 모든 정보 삭제
            adminQuizDAO.deleteUserAnswersBySetId(setId); // 사용자 답변 삭제
            adminQuizDAO.deleteQuizSetResultsBySetId(setId); // 퀴즈 세트 결과 삭제
            adminQuizDAO.deleteQuizSetQuestionsBySetId(setId); // 퀴즈 세트와 퀴즈 문제 연결 삭제

            // 퀴즈 세트 업데이트
            adminQuizDAO.updateQuizSet(quizSetsVO);

            // 새로운 퀴즈 문제와 퀴즈 세트 연결
            for (QuizVO quizVO : quizList) {
                int quizId = quizVO.getQuizId();
                adminQuizDAO.insertQuizSetQuestions(setId, quizId);
            }
        } catch (Exception e) {
            throw new RuntimeException("퀴즈 세트 수정 중 오류가 발생했습니다.", e);
        }
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
