package org.finterest.quiz.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizSetDetailsVO {
    private int setId;            // 퀴즈 세트 ID
    private String setName;       // 퀴즈 세트 이름
    private String categoryName;  // 카테고리 이름
    private List<QuizVO> questions; // 퀴즈 문제 목록
}
