package org.finterest.security.account.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.finterest.security.account.domain.UserVO;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {
    String username;
    String email;
    List<String> roles;

    public static UserInfoDTO of(UserVO member) {
        return new UserInfoDTO(member.getUsername(), member.getEmail(),
                member.getAuthList().stream()
                        .map(a-> a.getAuth())
                        .toList()
        );
    }
}
