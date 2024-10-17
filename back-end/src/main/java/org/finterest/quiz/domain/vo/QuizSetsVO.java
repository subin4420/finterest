package org.finterest.quiz.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)  // null인 필드는 직렬화하지 않음
public class QuizSetsVO {
    private int setId;             // set_id -> setId로 수정
    private String setName;        // set_name
    private String setImg;         // set_img
    private String categoryName;   // category_name
    private String createdAt;      // created_at
    private String updatedAt;      // updated_at

    @JsonInclude(JsonInclude.Include.NON_EMPTY)  // null 또는 빈 리스트일 경우 직렬화하지 않음
    private Integer userScore;  // 사용자가 해당 세트에서 얻은 점수 (로그인한 경우)

    @JsonInclude(JsonInclude.Include.NON_EMPTY)  // null 또는 빈 리스트일 경우 직렬화하지 않음
    private String completedAt;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)  // null 또는 빈 리스트일 경우 직렬화하지 않음
    private List<QuizVO> questions;  // 세트에 포함된 퀴즈 목록

    @JsonInclude(JsonInclude.Include.NON_EMPTY)  // null 또는 빈 리스트일 경우 직렬화하지 않음
    private Integer participationCount;  // 참여 횟수

    @JsonInclude(JsonInclude.Include.NON_EMPTY)  // null 또는 빈 리스트일 경우 직렬화하지 않음
    private Double averageScore;  // 평균 점수
}
