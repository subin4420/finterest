package org.finterest.security.account.mapper;

import org.finterest.security.account.domain.UserVO;


public interface UserDetailsMapper {
    public UserVO get(String username);
}

