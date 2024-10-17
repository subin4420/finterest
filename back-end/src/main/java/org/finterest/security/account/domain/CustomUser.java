<<<<<<<< HEAD:back-end/src/main/java/org/scoula/security/account/domain/CustomUser.java
package org.scoula.security.account.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
========
package org.finterest.security.account.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.GrantedAuthority;
>>>>>>>> 1b51b77c79417df63e65017f16894911d01af8c2:back-end/src/main/java/org/finterest/security/account/domain/CustomUser.java

import java.util.Collection;

@Getter
@Setter
public class CustomUser extends User {
<<<<<<<< HEAD:back-end/src/main/java/org/scoula/security/account/domain/CustomUser.java
    private MemberVO member;		// 실질적인 사용자 데이터

========
    private UserVO userVO;
>>>>>>>> 1b51b77c79417df63e65017f16894911d01af8c2:back-end/src/main/java/org/finterest/security/account/domain/CustomUser.java
    public CustomUser(String username, String password,
                      Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
<<<<<<<< HEAD:back-end/src/main/java/org/scoula/security/account/domain/CustomUser.java

    public CustomUser(MemberVO vo) {
        super(vo.getUsername(), vo.getPassword(), vo.getAuthList());
        this.member = vo;
    }

}
========
    public CustomUser(UserVO userVO){
        super(userVO.getUsername(), userVO.getPassword(), userVO.getAuthList());
        this.userVO = userVO;
    }
}
>>>>>>>> 1b51b77c79417df63e65017f16894911d01af8c2:back-end/src/main/java/org/finterest/security/account/domain/CustomUser.java
