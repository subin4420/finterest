package org.finterest.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.finterest.common.exception.PasswordMissmatchException;
import org.finterest.security.account.domain.AuthVO;
import org.finterest.security.account.domain.UserVO;
import org.finterest.user.dto.*;
import org.finterest.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@PropertySource({"classpath:/application.properties"})
public class UserServiceImpl implements UserService {
    final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Value("${avatar.path.unix}")
    String avatarPath;

    //중복체크
    @Override
    public boolean checkDuplicate(String username) {
        UserVO member = userMapper.get(username);
        return member != null ? true : false;
    }
    @Override
    public int getUserIdByUsername(String username) {
        return userMapper.getUserIdByUsername(username);
    }

    private void saveAvatar(MultipartFile avatar, String username) {
        //아바타 업로드
        if(avatar != null && !avatar.isEmpty()) {
            File dest = new File(avatarPath, username + ".png");
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

    public void changePassword(String username, ChangePasswordDTO changePasswordDTO) {
        // 사용자 정보 조회
        UserVO userVO = userMapper.findByUsername(username);
        if (userVO == null) {
            throw new UsernameNotFoundException("해당 사용자가 존재하지 않습니다.");
        }

        // 기존 비밀번호 확인 (optional)
        if (!passwordEncoder.matches(changePasswordDTO.getOldPassword(), userVO.getPassword())) {
            throw new IllegalArgumentException("이전 비밀번호를 잘못 입력하셨습니다.");
        }

        // 새로운 비밀번호 암호화 및 DTO 업데이트
        String encodedPassword = passwordEncoder.encode(changePasswordDTO.getNewPassword());
        changePasswordDTO.setNewPassword(encodedPassword);
        changePasswordDTO.setUsername(username);

        // 비밀번호 업데이트 호출
        userMapper.updatePassword(changePasswordDTO);
    }

    @Override
    public UserDTO update(UserUpdateDTO user) {
        UserVO vo = userMapper.get(user.getUsername());
        if(!passwordEncoder.matches(user.getPassword(),vo.getPassword())) {
            throw new PasswordMissmatchException();
        }
        userMapper.update(user.toVO());
        saveAvatar(user.getAvatar(), user.getUsername());
        return get(user.getUsername());
    }

    @Transactional
    public void updateLastLoginByUsername(String username){
        LocalDateTime today = LocalDateTime.now();
        userMapper.updateLastLoginByUsername(username, today );
    }
}
