package org.finterest.security.account.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
public class CustomUser extends User {
    private UserVO userVO;
    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
    public CustomUser(UserVO userVO){
        super(userVO.getUsername(), userVO.getPassword(), userVO.getAuthList());
    }
}