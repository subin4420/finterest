package org.scoula.security.account.mapper;

import org.scoula.security.account.domain.MemberVO;


public interface UserDetailsMapper {
    public MemberVO get(String username);
}
