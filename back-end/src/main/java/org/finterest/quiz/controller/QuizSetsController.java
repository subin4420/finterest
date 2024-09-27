package org.finterest.quiz.controller;

import org.finterest.quiz.domain.dto.QuizSubmissionDTO;
import org.finterest.quiz.domain.vo.QuizResultVO;
import org.finterest.quiz.domain.vo.QuizSetsVO;
import org.finterest.quiz.domain.vo.QuizVO;
import org.finterest.quiz.domain.vo.UserAnswerVO;
import org.finterest.quiz.service.QuizSetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/api/quiz-sets")
public class QuizSetsController {
    private final QuizSetsService quizSetsService;

    @Autowired
    public QuizSetsController(QuizSetsService quizSetsService) {
        this.quizSetsService = quizSetsService;
    }

    // JWT 토큰에서 사용자 ID 추출 메서드 (가정: JWT 유틸리티 클래스 사용)
    private int getUserIdFromToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");

        // JWT 유틸리티 클래스를 통해 userId를 추출
        //return JwtUtils.getUserIdFromToken(token);
        return 1;   // 테스트
    }

//    // 전체 퀴즈 세트 조회 (category_id가 없는 경우)
//    @GetMapping
//    public Map<String, List<QuizSetsVO>> selectAllQuizSets() {
//        List<QuizSetsVO> quizSetsVOList = quizSetsService.selectAllQuizSets();
//        Map<String, List<QuizSetsVO>> response = new HashMap<>();
//        response.put("quiz_sets", quizSetsVOList);
//        return response;
//    }

    @GetMapping("/quiz-sets")
    public ResponseEntity<Map<String, Object>> getQuizSets(@RequestHeader(value = "Authorization", required = false) String token) {
        List<QuizSetsVO> quizSets = quizSetsService.selectAllQuizSets();

        Map<String, Object> response = new HashMap<>();
        response.put("quiz_sets", quizSets);

        // JWT 토큰이 있으면 로그인한 사용자로 간주하고, 퀴즈 세트 점수와 완료 날짜를 추가
        if (token != null && !token.isEmpty()) {
            // String userId = jwtUtils.getUserIdFromJwtToken(token);  // JWT에서 userId를 추출
            int userId = 1;  // 임시로 설정된 userId
            Map<Integer, Map<String, Object>> quizResults = quizSetsService.getQuizResultsForUser(userId);

            // 퀴즈 세트에 해당하는 점수와 완료 날짜를 포함해 응답에 추가
            for (QuizSetsVO quizSet : quizSets) {
                if (quizResults.containsKey(quizSet.getSetId())) {
                    Map<String, Object> resultData = quizResults.get(quizSet.getSetId());
                    quizSet.setUserScore((Integer) resultData.get("score"));  // 점수 설정
                    quizSet.setCompletedAt((String) resultData.get("completedAt"));  // 완료 날짜 설정
                }
            }
        }

        return ResponseEntity.ok(response);
    }

//    // 카테고리별 퀴즈 세트 조회 (category_id가 있는 경우)
//    @GetMapping(params = "category_id")
//    public Map<String, List<QuizSetsVO>> selectQuizSets(@RequestParam("category_id") int categoryId) {
//        List<QuizSetsVO> quizSetsVOList = quizSetsService.selectQuizSetsByCategory(categoryId);
//        Map<String, List<QuizSetsVO>> response = new HashMap<>();
//        response.put("quiz_sets", quizSetsVOList);
//        return response;
//    }

    // 카테고리별 또는 전체 퀴즈 세트 조회
    @GetMapping
    public Map<String, List<QuizSetsVO>> selectQuizSets(
            @RequestParam(value = "categoryId", required = false) Integer categoryId,
            @RequestHeader(value = "Authorization", required = false) String token) {

        List<QuizSetsVO> quizSetsVOList;

        // 카테고리 ID가 있는 경우, 해당 카테고리의 퀴즈 세트만 조회
        if (categoryId != null) {
            quizSetsVOList = quizSetsService.selectQuizSetsByCategory(categoryId);
        } else {
            quizSetsVOList = quizSetsService.selectAllQuizSets();
        }

        // JWT 토큰이 있으면 로그인한 사용자로 간주하고, 퀴즈 세트 점수를 추가
        if (token != null && !token.isEmpty()) {
            //String userId = jwtUtils.getUserIdFromJwtToken(token);  // JWT에서 userId 추출
            int userId = 1;
            Map<Integer, Map<String, Object>> quizResults = quizSetsService.getQuizResultsForUser(userId);

            // 퀴즈 세트에 해당하는 점수와 완료 날짜를 포함해 응답에 추가
            for (QuizSetsVO quizSet : quizSetsVOList) {
                if (quizResults.containsKey(quizSet.getSetId())) {
                    Map<String, Object> resultData = quizResults.get(quizSet.getSetId());
                    quizSet.setUserScore((Integer) resultData.get("score"));  // 점수 설정
                    quizSet.setCompletedAt((String) resultData.get("completedAt"));  // 완료 날짜 설정
                }
            }
        }

        // 응답 구성
        Map<String, List<QuizSetsVO>> response = new HashMap<>();
        response.put("quiz_sets", quizSetsVOList);
        return response;
    }


    // 특정 퀴즈 세트와 해당 문제 목록 조회
    @GetMapping("/{setId}/questions")
    public ResponseEntity<QuizSetsVO> getQuizSetWithQuestions(@PathVariable int setId) {
        QuizSetsVO quizSet = quizSetsService.selectQuizSetById(setId);

        if (quizSet == null) {
            return ResponseEntity.notFound().build();  // 퀴즈 세트가 존재하지 않는 경우
        }

        List<QuizVO> questions = quizSetsService.selectQuestionsBySetId(setId);
        quizSet.setQuestions(questions);  // 문제 목록을 퀴즈 세트에 추가

        return ResponseEntity.ok(quizSet);
    }



    // 퀴즈 제출 API
    @PostMapping("/{setId}/submit")
    public ResponseEntity<QuizResultVO> submitQuiz(
            @PathVariable("setId") int setId,
            @RequestBody QuizSubmissionDTO submission,
            @RequestHeader("Authorization") String token) {

        // JWT 토큰에서 사용자 ID 추출
        String jwtToken = token.replace("Bearer ", "");
        //int userId = jwtUtils.getUserIdFromJwtToken(jwtToken);
        int userId = 1;     // 테스트

        // 제출된 퀴즈 데이터를 처리하고 결과 반환
        QuizResultVO result = quizSetsService.submitQuiz(setId, submission);
        return ResponseEntity.ok(result);
    }



    // 퀴즈 결과 조회 API
    @GetMapping("/{setId}/results")
    public ResponseEntity<Map<String, Object>> getQuizResult(
            @PathVariable("setId") int setId,
            @RequestHeader("Authorization") String token) {
        // JWT에서 userId 추출 (임시로 userId를 1로 설정)
        //int userId = jwtUtils.getUserIdFromJwtToken(token);
        int userId = 1;

        // 퀴즈 결과 조회
        QuizResultVO quizResult = quizSetsService.getQuizResult(userId, setId);

        if (quizResult == null) {
            return ResponseEntity.notFound().build();
        }

        // 필요한 필드만 응답에 포함
        Map<String, Object> response = new HashMap<>();
        response.put("resultId", quizResult.getResultId());
        response.put("userId", quizResult.getUserId());
        response.put("setId", quizResult.getSetId());
        response.put("totalScore", quizResult.getTotalScore());
        response.put("maxScore", quizResult.getMaxScore());
        response.put("completedAt", quizResult.getCompletedAt());

        return ResponseEntity.ok(response);
    }


    // 사용자 답변 목록 조회 API
    @GetMapping("/{setId}/results/{resultId}/answers")
    public ResponseEntity<Map<String, Object>> getUserAnswers(@PathVariable int setId,
                                                              @PathVariable int resultId,
                                                              @RequestHeader("Authorization") String token) {
        //int userId = jwtUtils.getUserIdFromJwtToken(token);  // JWT 토큰에서 사용자 ID를 추출
        int userId = 1;

        List<UserAnswerVO> answers = quizSetsService.getUserAnswers(resultId, userId);

        // 응답을 구성하는 JSON
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultId", resultId);
        response.put("userId", userId);
        response.put("setId", setId);
        response.put("answers", answers);

        return ResponseEntity.ok(response);
    }
}
