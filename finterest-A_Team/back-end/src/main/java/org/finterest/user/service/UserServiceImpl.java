package org.finterest.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.finterest.security.account.domain.AuthVO;
import org.finterest.security.account.domain.UserVO;
import org.finterest.user.dto.UserDTO;
import org.finterest.user.dto.UserJoinDTO;
import org.finterest.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

//    @Autowired
//    public UserService(UserMapper userMapper) {
//        this.userMapper = userMapper;
//    }

    // 회원 가입 처리

    @Override
    public boolean checkDuplicate(String username) {
        UserVO member = userMapper.get(username);
        return member != null ? true : false;
    }
    @Transactional
    @Override
    public UserDTO join(UserJoinDTO userJoinDTO) {
        UserVO userVO = userJoinDTO.toVO();

        userVO.setPassword(passwordEncoder.encode(userVO.getPassword()));
        userMapper.insert(userVO);

        AuthVO auth = new AuthVO();
        auth.setUsername(userVO.getUsername());
        auth.setAuth("ROLE_MEMBER");

        userMapper.insertAuth(auth);
        //saveAvatar(UserJoinDTO.getAvatar(), userVO.getUsername());
        return get(userVO.getUsername());
    }

    @Override
    public UserDTO delete(String username) {
        UserVO userVO = userMapper.get(username);
        userMapper.delete(username);
        return UserDTO.of(userVO);
    }
    @Override
    public UserDTO get(String username) {
        System.out.println("username: " + username);
        UserVO userVO = Optional.ofNullable(userMapper.get(username)).orElseThrow(NoSuchElementException::new);
        return UserDTO.of(userVO);
    }
    @Override
    public List<UserDTO> getList() {
        List<UserVO> userVOList = userMapper.getList();
        return userVOList.stream().map(UserDTO::of).collect(Collectors.toList());
    }


}
