package org.finterest.user.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.finterest.security.account.domain.AuthVO;
import org.finterest.security.account.domain.UserVO;
import org.finterest.user.dto.UserDTO;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Mapper
public interface UserMapper {

    List<UserVO> getList();

    UserVO get(String username);

    int update(UserVO member);

    int insert(UserVO member);

    //int updatePassword(ChangePasswordDTO changePasswordDTO);

    int delete(String username);

    int insertAuth(AuthVO auth);

    int deleteAuth(AuthVO auth);

}
