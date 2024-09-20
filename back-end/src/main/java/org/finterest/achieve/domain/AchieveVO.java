package org.finterest.achieve.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AchieveVO {
    private int material_id;       // 학습 자료 ID
    //private int categoryId;       // 카테고리 ID (FK)
    private String title;         // 학습 자료 제목
    private String material_img;   // 썸네일 이미지
    private String link;          // 유튜브 링크
    private String description;   // 학습 자료 설명
    private String content;       // 학습 자료 내용
    private Date createdAt;       // 학습 자료 생성일
    private Date updatedAt;       // 학습 자료 수정일

    private String categoryName;  // category_name (조인된 값)
}
