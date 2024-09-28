package org.finterest.security.account.domain;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.finterest.security.account.domain.AuthVO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserVO {

    private int userId;  // user_id (Primary Key)
    private String username;  // 사용자 ID
    private String password;  // 비밀번호
    private String email;  // 이메일
    private String fullName;  // 실명
    private String accountStatus;  // 계정 상태 ('active' or 'inactive')
    private BigDecimal money;  // 보유 금액
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date signupDate;  // 가입일
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date lastLogin;  // 마지막 로그인 시간
    private int totalPoints;  // 누적 포인트

    // 권한 목록, Join 처리 필요
    private List<AuthVO> authList;

}

//CREATE TABLE Users (
//    user_id INT PRIMARY KEY AUTO_INCREMENT,  -- 사용자 고유 index
//    username VARCHAR(50) NOT NULL UNIQUE,  -- ID (고유)
//    password VARCHAR(255) NOT NULL,  -- 비밀번호
//    email VARCHAR(100) UNIQUE NOT NULL,  -- 이메일 (고유)
//    full_name VARCHAR(100) NOT NULL,  -- 실명
//    account_status VARCHAR(20) DEFAULT 'active',  -- 계정 상태 (기본값: 'active')
//    money DECIMAL(15,2) DEFAULT 10000000,  -- 보유 금액 (기본값: 10,000,000)
//    signup_date DATE NOT NULL,  -- 가입일
//    last_login DATETIME,  -- 마지막 로그인 시간
//    total_points INT DEFAULT 0  -- 누적 포인트
//);
