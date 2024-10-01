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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
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
    public ResponseEntity<UserDTO> join(@ModelAttribute UserJoinDTO userJoinDTO) {
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
        String avatarPath = "/Users/park/Desktop/최종 프로젝트/avatar/" + username + ".png";
        File file = new File(avatarPath);
        if(!file.exists()) {
            file = new File("/Users/park/Desktop/최종 프로젝트/avatar/unknown.png");
        }
        UploadFiles.downloadImage(response, file);
    }


}


