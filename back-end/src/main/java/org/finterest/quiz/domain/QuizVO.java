package org.finterest.quiz.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizVO {
    private int quizId;       // 퀴즈 ID
    private String question;  // 퀴즈 질문
    private String choice1;   // 선택지 1
    private String choice2;   // 선택지 2
    private String choice3;   // 선택지 3
    private String choice4;   // 선택지 4
    private int correctChoice; // 정답 번호

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date createdAt;   // 퀴즈 생성일
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date updatedAt;   // 퀴즈 수정일
}
