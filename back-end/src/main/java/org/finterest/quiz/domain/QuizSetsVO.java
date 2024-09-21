package org.finterest.quiz.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
