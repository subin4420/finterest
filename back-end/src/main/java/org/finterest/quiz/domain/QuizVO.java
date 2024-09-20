package org.finterest.quiz.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizVO {

    private int setId;          // 퀴즈 세트 고유 ID
    private String setName;     // 퀴즈 세트 이름
    private String setImg;      // 퀴즈 세트 이미지 링크
    private String categoryName; // 카테고리 이름
    private Date createdAt;     // 세트 생성일
    private Date updatedAt;     // 세트 수정일

}
