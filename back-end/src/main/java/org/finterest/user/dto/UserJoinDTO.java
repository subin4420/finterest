package org.finterest.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.finterest.security.account.domain.UserVO;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserJoinDTO {
    String username;
    String password;
    String fullName;
    String email;
    String accountStatus = "active";  // 기본값
    BigDecimal money = new BigDecimal("0");  // 기본값

    MultipartFile avatar;


    public UserVO toVO() {
        return UserVO.builder()
                .username(username)
                .password(password)
                .email(email)
                .fullName(fullName)
                .accountStatus(accountStatus)
                .money(money)
                .build();
    }
}
