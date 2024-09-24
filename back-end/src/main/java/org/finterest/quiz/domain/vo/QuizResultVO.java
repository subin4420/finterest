package org.finterest.quiz.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizResultVO {
    private int resultId;
    private int setId;  // 퀴즈 세트 ID 추가
    private int userId;  // 사용자 ID 추가
    private int totalScore;
    private int maxScore;
    private String completedAt;  // 퀴즈 완료 날짜 필드 추가
    private List<CorrectAnswer> correctAnswers;
    private List<WrongAnswer> wrongAnswers;

    // Getters and setters

    // 내부 클래스 CorrectAnswer 정의
    public static class CorrectAnswer {
        private int quizId;
        private int correctChoice;

        public CorrectAnswer(int quizId, int correctChoice) {
            this.quizId = quizId;
            this.correctChoice = correctChoice;
        }

        // Getters and setters
        public int getQuizId() {
            return quizId;
        }

        public void setQuizId(int quizId) {
            this.quizId = quizId;
        }

        public int getCorrectChoice() {
            return correctChoice;
        }

        public void setCorrectChoice(int correctChoice) {
            this.correctChoice = correctChoice;
        }
    }

    // 내부 클래스 WrongAnswer 정의
    public static class WrongAnswer {
        private int quizId;
        private int selectedChoice;

        public WrongAnswer(int quizId, int selectedChoice) {
            this.quizId = quizId;
            this.selectedChoice = selectedChoice;
        }

        // Getters and setters
        public int getQuizId() {
            return quizId;
        }

        public void setQuizId(int quizId) {
            this.quizId = quizId;
        }

        public int getSelectedChoice() {
            return selectedChoice;
        }

        public void setSelectedChoice(int selectedChoice) {
            this.selectedChoice = selectedChoice;
        }
    }
}
