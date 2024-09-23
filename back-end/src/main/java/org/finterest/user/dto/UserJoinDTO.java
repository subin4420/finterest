package org.finterest.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.finterest.security.account.domain.UserVO;
import org.springframework.security.core.userdetails.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserJoinDTO {
    String username;
    String password;
    String fullName;
    String email;
    public UserVO toVO() {
        return UserVO.builder()
                .username(username)
                .password(password)
                .email(email)
                .fullName(fullName)
                .build();
    }
}
