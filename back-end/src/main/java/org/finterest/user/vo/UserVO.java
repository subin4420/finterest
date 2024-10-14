package org.finterest.user.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {
    private int userId;
    private String username;
    private String email;
    private String fullName;
    private String accountStatus;
    private String signupDate;
    private String lastLogin;
    private int totalPoints;
    private int rankPosition;
    private String rankTitle;
    private String roles;  // 권한 목록을 하나의 문자열로 처리
}
