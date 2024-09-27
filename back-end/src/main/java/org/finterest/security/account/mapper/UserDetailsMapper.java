package org.finterest.security.account.mapper;

import org.finterest.security.account.domain.MemberVO;


public interface UserDetailsMapper {
    public MemberVO get(String username);
}
