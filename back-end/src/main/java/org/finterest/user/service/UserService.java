package org.finterest.user.service;

import org.finterest.user.dto.*;


import java.io.IOException;
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
    int getUserIdByUsername(String username);
    List<UserDTO> getList();
    boolean verifyUser(UserVerificationDTO request); // 사용자 확인 메서드
    void changePassword(String username, ChangePasswordDTO changePasswordDTO); // 비밀번호 변경 메서드
    //boolean resetPassword(String username, ChangePasswordDTO request); // 비밀번호 변경 요청 처
    //비밀번호변경
    //void changePassword(ChangePasswordDTO changePassword);
    //회원 정보 수정
    UserDTO update(UserUpdateDTO user);
    //void updateUser(UserUpdateDTO userUpdateDTO) throws IOException;
}
