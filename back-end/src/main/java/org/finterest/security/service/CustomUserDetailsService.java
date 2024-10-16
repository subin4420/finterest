package org.finterest.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.finterest.security.account.domain.CustomUser;
import org.finterest.security.account.domain.UserVO;
import org.finterest.security.account.mapper.UserDetailsMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Log4j
@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    final private UserDetailsMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserVO vo = mapper.get(username);
        if(vo == null) {
            throw new UsernameNotFoundException(username + "은 없는 id입니다.");
        }

        return new CustomUser(vo);
    }

}
