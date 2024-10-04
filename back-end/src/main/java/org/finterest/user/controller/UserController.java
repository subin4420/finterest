package org.finterest.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.finterest.common.util.UploadFiles;
import org.finterest.user.dto.ChangePasswordDTO;
import org.finterest.user.dto.UserDTO;
import org.finterest.user.dto.UserJoinDTO;
import org.finterest.user.dto.UserVerificationDTO;
import org.finterest.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Value("${avatar.path}")  // application.properties에서 경로 주입
    private String avatarPath;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    // 모든 사용자 조회
    @GetMapping
    public ResponseEntity<List<UserDTO>> getList() {
        return ResponseEntity.ok(userService.getList());
    }
    // 특정 사용자 정보 조회
    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> get(@PathVariable String username) {
        return ResponseEntity.ok(userService.get(username));
    }
    //회원가입
    @PostMapping("/join")
    public ResponseEntity<UserDTO> join(UserJoinDTO userJoinDTO) {
        System.out.println("username :  " + userJoinDTO.getUsername());
        System.out.println("password :  " + userJoinDTO.getPassword());
        return ResponseEntity.ok(userService.join(userJoinDTO));
    }
    //중복체크
    @GetMapping("/checkusername/{username}")
    public ResponseEntity<Boolean> checkUsername(@PathVariable String username) {
        return ResponseEntity.ok().body(userService.checkDuplicate(username));
    }
    //회원탈퇴
    @DeleteMapping("/{username}")
    public ResponseEntity<UserDTO> delete(@PathVariable String username) {
        return ResponseEntity.ok(userService.delete(username));
    }
    @GetMapping("/{username}/avatar")
    public void getAvatar(@PathVariable String username, HttpServletResponse response) {
        String filePath = avatarPath + "/" + username + ".png";
        System.out.println("UserController-> getAvatar:->"+avatarPath);
        File file = new File(filePath);
        if(!file.exists()) {
            file = new File(avatarPath+"/unknown.png");
        }
        UploadFiles.downloadImage(response, file);
    }
    @GetMapping("/findId/{username}")
    public ResponseEntity<Integer> findById(@PathVariable String username) {
        try {
            // UserService를 사용해 username으로 사용자 정보 조회
            UserDTO userDTO = userService.get(username);
            int id = userDTO.toVO().getUserId();
            System.out.println("in findId/username: =>"+id);
            // 사용자 정보가 존재하면 200 OK와 함께 반환
            return ResponseEntity.ok(userDTO.toVO().getUserId());
        } catch (NoSuchElementException e) {
            // 사용자를 찾지 못한 경우 404 Not Found 응답 반환
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}


