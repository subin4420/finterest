package org.finterest.security.account.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserVO {
    //바꿔야함
    private String username;
    private String password;
    private String email;
    private Date regDate;
    private Date updateDate;

    private List<AuthVO> authList;		// 권한 목록, join 처리 필요
}
