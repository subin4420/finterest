package org.finterest.user.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.finterest.security.account.domain.AuthVO;
import org.finterest.security.account.domain.UserVO;
import org.finterest.user.dto.ChangePasswordDTO;
import org.finterest.user.dto.UserVerificationDTO;

import org.finterest.user.dto.UserDTO;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Mapper
public interface UserMapper {

    
    List<UserVO> getList();
    // username으로 user_id 조회
    int getUserIdByUsername(String username);

    UserVO get(String username);

    int update(UserVO member);

    int insert(UserVO member);

    //사용자의 비밀번호 재설정
    int updatePassword(ChangePasswordDTO changePasswordDTO);

    //유저아이디 이름 이메일로 사용자 찾기(비밀번호 찾기하기위한)
    UserVO findUserByDetails(UserVerificationDTO userVerificationDTO);

    UserVO findByUsername(@Param("username") String username);

    int delete(String username);

    int insertAuth(AuthVO auth);

    int deleteAuth(AuthVO auth);

    void updateUser(UserVO updatedUser);
}
