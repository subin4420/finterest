package org.finterest.quiz.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizSetsVO {

    private int setId;          // 퀴즈 세트 고유 ID
    private String setName;     // 퀴즈 세트 이름
    private String setImg;      // 퀴즈 세트 이미지 링크
    private String categoryName; // 카테고리 이름

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date createdAt;     // 세트 생성일
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date updatedAt;     // 세트 수정일

}
