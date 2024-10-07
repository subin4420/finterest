package org.finterest.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.finterest.security.account.domain.UserVO;
import org.springframework.web.multipart.MultipartFile;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateDTO {
    private String username;
    private String fullName;
    private String password;
    private String email;

    MultipartFile avatar;



    public UserVO toVO() {
        return UserVO.builder()
                .username(username)
                .fullName(fullName)
                .email(email)
                .build();
    }
}
