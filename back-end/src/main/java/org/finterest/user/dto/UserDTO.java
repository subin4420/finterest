package org.finterest.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.finterest.security.account.domain.UserVO;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private String username;  // username
    //private String password;  // password
    private String email;  // email
    private String fullName;  // full_name
    private String accountStatus;  // account_status (Default: 'active')
    private BigDecimal money;  // 보유 금액 (Default: 10,000,000)
    private Date signupDate;  // 가입일
    private Date lastLogin;  // 마지막 로그인 시간
    private int totalPoints;  // 누적 포인트 (Default: 0)

    MultipartFile avatar;  // 아바타 이미지

    private List<String> authList;  // 권한 목록, join 처리 필요

    // UserVO를 UserDTO로 변환
    public static UserDTO of(UserVO u) {
        return UserDTO.builder()
                .username(u.getUsername())
//                .password(u.getPassword())  // 보안상 주석 처리
                .email(u.getEmail())
                .fullName(u.getFullName())
                .accountStatus(u.getAccountStatus())
                .money(u.getMoney())
                .signupDate(u.getSignupDate())
                .lastLogin(u.getLastLogin())
                .totalPoints(u.getTotalPoints())
                .authList(u.getAuthList().stream().map(a -> a.getAuth()).toList())  // 권한 목록을 String 리스트로 변환
                .build();
    }

    // UserDTO를 UserVO로 변환
    public UserVO toVO() {
        return UserVO.builder()
                .username(username)
//                .password(password)  // 보안상 주석 처리
                .email(email)
                .fullName(fullName)
                .accountStatus(accountStatus)
                .money(money)
                .signupDate(signupDate)
                .lastLogin(lastLogin)
                .totalPoints(totalPoints)
                .build();
    }
}
