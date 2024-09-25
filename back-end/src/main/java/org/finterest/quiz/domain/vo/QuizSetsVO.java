package org.finterest.quiz.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizSetsVO {
    private int setId;             // set_id -> setId로 수정
    private String setName;        // set_name
    private String setImg;         // set_img
    private String categoryName;   // category_name
    private String createdAt;      // created_at
    private String updatedAt;      // updated_at
    private Integer userScore;  // 사용자가 해당 세트에서 얻은 점수 (로그인한 경우)
    private String completedAt;

    private List<QuizVO> questions;  // 세트에 포함된 퀴즈 목록
}
