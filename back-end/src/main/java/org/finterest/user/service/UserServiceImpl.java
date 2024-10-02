package org.finterest.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.finterest.security.account.domain.AuthVO;
import org.finterest.security.account.domain.UserVO;
import org.finterest.user.dto.ChangePasswordDTO;
import org.finterest.user.dto.UserDTO;
import org.finterest.user.dto.UserJoinDTO;
import org.finterest.user.dto.UserVerificationDTO;
import org.finterest.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    //중복체크
    @Override
    public boolean checkDuplicate(String username) {
        UserVO member = userMapper.get(username);
        return member != null ? true : false;
    }


    private void saveAvatar(MultipartFile avatar, String username) {
        //아바타 업로드
        if(avatar != null && !avatar.isEmpty()) {
            File dest = new File("/Users/park/Desktop/최종 프로젝트/avatar/", username + ".png");
            try {
                avatar.transferTo(dest);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    // 회원 가입 처리
    @Transactional
    @Override
    public UserDTO join(UserJoinDTO userJoinDTO) {
        UserVO userVO = userJoinDTO.toVO();

        userVO.setPassword(passwordEncoder.encode(userVO.getPassword()));
        userMapper.insert(userVO);

        AuthVO auth = new AuthVO();
        auth.setUsername(userVO.getUsername());
        auth.setAuth("ROLE_USER");

        userMapper.insertAuth(auth);
        saveAvatar(userJoinDTO.getAvatar(), userVO.getUsername());
        return get(userVO.getUsername());
    }
    //특정 회원 삭제
    @Override
    public UserDTO delete(String username) {
        UserVO userVO = userMapper.get(username);
        userMapper.delete(username);
        return UserDTO.of(userVO);
    }
    //특정회원 확인
    @Override
    public UserDTO get(String username) {
        System.out.println("username: " + username);
        UserVO userVO = Optional.ofNullable(userMapper.get(username)).orElseThrow(NoSuchElementException::new);
        return UserDTO.of(userVO);
    }
    //전체 유저
    @Override
    public List<UserDTO> getList() {
        List<UserVO> userVOList = userMapper.getList();
        return userVOList.stream().map(UserDTO::of).collect(Collectors.toList());
    }

    @Override
    public boolean verifyUser(UserVerificationDTO request) {
        UserVO user = userMapper.findUserByDetails(request);
        return user != null; // 사용자 존재 여부 반환
    }

    @Override
    public void changePassword(ChangePasswordDTO changePasswordDTO) {
        userMapper.updatePassword(changePasswordDTO); // DTO를 사용하여 비밀번호 업데이트
    }

}
