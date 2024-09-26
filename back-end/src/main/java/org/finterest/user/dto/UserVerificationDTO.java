package org.finterest.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.finterest.security.account.domain.UserVO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVerificationDTO {
    private String username;  // 사용자 ID
    private String fullName;  // 실명
    private String email;     // 이메일
    // DTO를 VO로 변환하는 메서드
    public UserVO toUserVO() {
        UserVO userVO = new UserVO();
        userVO.setUsername(this.username);
        userVO.setFullName(this.fullName);
        userVO.setEmail(this.email);
        return userVO;
    }
}