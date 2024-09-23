package org.finterest.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.finterest.user.dto.UserDTO;
import org.finterest.user.dto.UserJoinDTO;
import org.finterest.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/join")
    public ResponseEntity<UserDTO> join(UserJoinDTO member) {
        return ResponseEntity.ok(userService.join(member));
    }

    //비밀번호 바꾸기
//    @PutMapping("/{username}/changepassword")
//    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
//        userService.changePassword(changePasswordDTO);
//        return ResponseEntity.ok().build();
//    }
//
//    @PutMapping("/{username}")
//    public ResponseEntity<UserDTO> changeProfile(UserUpdateDTO user) {
//        return ResponseEntity.ok(userService.update(user));
//    }
    @DeleteMapping("/{username}")
    public ResponseEntity<UserDTO> delete(@PathVariable String username) {
        return ResponseEntity.ok(userService.delete(username));
    }

}


