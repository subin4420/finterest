package org.finterest.achieve.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AchieveVO {
    private int materialId;       // 학습 자료 ID
    //private int categoryId;     // 카테고리 ID (FK)
    private String title;         // 학습 자료 제목
    private String materialImg;   // 썸네일 이미지
    private String link;          // 유튜브 링크
    private String description;   // 학습 자료 설명
    private String content;       // 학습 자료 내용

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date createdAt;       // 학습 자료 생성일
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date updatedAt;       // 학습 자료 수정일

    private String categoryName;  // category_name (조인된 값)
}
