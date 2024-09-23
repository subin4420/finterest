package org.finterest.user.service;

import org.finterest.user.dto.UserDTO;
import org.finterest.user.dto.UserJoinDTO;

import java.util.List;

public interface UserService {
    //중복확인
    boolean checkDuplicate(String username);
    //username으로 검색
    UserDTO get(String username);
    //회원가입
    UserDTO join(UserJoinDTO user);
    //아이디 삭제
    UserDTO delete(String username);

    List<UserDTO> getList();
    //비밀번호변경
    //void changePassword(ChangePasswordDTO changePassword);
    //회원 정보 수정
    //UserDTO update(UserUpdateDTO user);

}
