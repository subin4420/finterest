package org.finterest.security.account.mapper;

import lombok.extern.log4j.Log4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.finterest.common.config.RootConfig;
import org.finterest.security.account.domain.AuthVO;
import org.finterest.security.account.domain.UserVO;
import org.finterest.security.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { RootConfig.class, SecurityConfig.class })
@Log4j
public class UserDetailsMapperTest {
    @Autowired
    private UserDetailsMapper mapper;

    @Test
    public void testGet() {
        UserVO member = mapper.get("user1");
        log.info(member);

        for(AuthVO auth : member.getAuthList()) {
            log.info(auth);
        }
    }
}
