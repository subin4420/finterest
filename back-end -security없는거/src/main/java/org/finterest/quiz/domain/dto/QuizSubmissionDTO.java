package org.finterest.quiz.domain.dto;

import java.util.List;

public class QuizSubmissionDTO {
    private int userId;
    private List<AnswerDTO> answers;

    public static class AnswerDTO {
        private int quizId;
        private int selectedChoice;

        // Getters and Setters
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

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<AnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDTO> answers) {
        this.answers = answers;
    }
}
