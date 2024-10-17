package org.finterest.quiz.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)  // null인 필드는 직렬화하지 않음
public class QuizVO {
    private int quizId;
    private String question;
    private String choice1;
    private String choice2;
    private String choice3;
    private String choice4;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)  // null 또는 빈 리스트일 경우 직렬화하지 않음
    private String correctChoice;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)  // null 또는 빈 리스트일 경우 직렬화하지 않음
    private int categoryId;
}
