package org.finterest.quiz.controller;

import org.finterest.archive.domain.ArchiveVO;
import org.finterest.quiz.domain.dto.QuizSubmissionDTO;
import org.finterest.quiz.domain.vo.*;
import org.finterest.quiz.service.QuizSetsService;
import org.finterest.security.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/quiz-sets")
public class QuizSetsController {
    private final QuizSetsService quizSetsService;
    private final TokenUtil tokenUtil;

    @Autowired
    public QuizSetsController(QuizSetsService quizSetsService, TokenUtil tokenUtil) {
        this.quizSetsService = quizSetsService;
        this.tokenUtil = tokenUtil;
    }

    // 전체 퀴즈 세트 조회 (category_id가 없는 경우)
    @GetMapping("/quiz-sets")
    public ResponseEntity<Map<String, Object>> getQuizSets(@RequestHeader(value = "Authorization", required = false) String token) {
        List<QuizSetsVO> quizSets = quizSetsService.selectAllQuizSets();

        Map<String, Object> response = new HashMap<>();
        response.put("quizSets", quizSets);

        // JWT 토큰이 있으면 로그인한 사용자로 간주하고, 퀴즈 세트 점수와 완료 날짜를 추가
        if (token != null && !token.isEmpty()) {
            int userId = tokenUtil.getUserIdFromToken(token);

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

    // 카테고리별 또는 전체 퀴즈 세트 조회
    @GetMapping
    //http://localhost:8080/api/quiz-sets?categoryId=1
    //GET /api/quiz-sets?categoryId=1
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
            int userId = tokenUtil.getUserIdFromToken(token);
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
        response.put("quizSets", quizSetsVOList);
        return response;
    }


    // 2. 특정 퀴즈 세트와 해당 문제 목록 조회
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



    // 4. 퀴즈 제출 API
    @PostMapping("/{setId}/submit")
    public ResponseEntity<QuizResultDetailVO> submitQuiz(
            @PathVariable("setId") int setId,
            @RequestBody QuizSubmissionDTO submission,
            @RequestHeader("Authorization") String token) {

        int userId = tokenUtil.getUserIdFromToken(token);

        // 제출된 퀴즈 데이터를 처리하고 결과 반환
        QuizResultDetailVO result = quizSetsService.submitQuiz(setId, userId ,submission);
        return ResponseEntity.ok(result);
    }



    // 5. 퀴즈 결과 조회 API
    @GetMapping("/results")
    public ResponseEntity<Map<String, Object>> getQuizResults(
            @RequestHeader("Authorization") String token) {

        // 토큰에서 userId 추출
        int userId = tokenUtil.getUserIdFromToken(token);

        // 퀴즈 결과 조회
        List<QuizResultVO> quizResults = quizSetsService.getQuizResult(userId);

        if (quizResults == null || quizResults.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // 결과를 JSON 형태로 응답에 포함시키기 위한 Map 구성
        Map<String, Object> response = new HashMap<>();
        response.put("quizResults", quizResults);

        return ResponseEntity.ok(response);
    }



    // 6. 사용자 답변 목록 조회 API
    @GetMapping("/{setId}/results/{resultId}/answers")
    public ResponseEntity<Map<String, Object>> getUserAnswers(
            @PathVariable int setId,
            @PathVariable int resultId,
            @RequestHeader("Authorization") String token) {

        // 토큰에서 사용자 ID를 추출
        int userId = tokenUtil.getUserIdFromToken(token);

        // 사용자 답변 목록 조회
        List<UserAnswerVO> answers = quizSetsService.getUserAnswers(resultId, userId);

        // 퀴즈 세트와 관련된 추가 정보 조회 (퀴즈 세트 이름, 이미지, 카테고리 이름 등)
        QuizSetsVO quizSet = quizSetsService.selectQuizSetById(setId);

        if (quizSet == null || answers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "퀴즈 세트 또는 답변을 찾을 수 없습니다."));
        }

        // 응답을 구성하는 JSON
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultId", resultId);
        response.put("userId", userId);
        response.put("setId", setId);
        response.put("setName", quizSet.getSetName());
        response.put("setImg", quizSet.getSetImg());
        response.put("categoryName", quizSet.getCategoryName());
        response.put("answers", answers);

        return ResponseEntity.ok(response);
    }

    // 완료 횟수가 높은 퀴즈 세트 상위 3개 조회 API
    @GetMapping("/top-completed")
    public Map<String, List<QuizSetsVO>> getTopCompletedQuizSets() {
        List<QuizSetsVO> topCompletedQuizSets = quizSetsService.getTopCompletedQuizSets();
        Map<String, List<QuizSetsVO>> response = new HashMap<>();
        response.put("completedQuizSets", topCompletedQuizSets);
        return response;
    }
}
