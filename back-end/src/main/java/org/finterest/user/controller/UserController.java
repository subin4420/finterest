package org.finterest.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.finterest.common.util.UploadFiles;
import org.finterest.user.dto.*;
import org.finterest.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<UserDTO> join(@ModelAttribute UserJoinDTO userJoinDTO) {
        System.out.println("username :  " + userJoinDTO.getUsername());
        System.out.println("password :  " + userJoinDTO.getPassword());
        return ResponseEntity.ok(userService.join(userJoinDTO));
    }
    //중복체크
    @GetMapping("/checkusername/{username}")
    public ResponseEntity<Boolean> checkUsername(@PathVariable String username) {
        return ResponseEntity.ok().body(userService.checkDuplicate(username));
    }
    @PutMapping("/{username}")
    public ResponseEntity<UserDTO> changeProfile(UserUpdateDTO member) {
        return ResponseEntity.ok(userService.update(member));
    }
    //회원탈퇴
    @DeleteMapping("/{username}")
    public ResponseEntity<UserDTO> delete(@PathVariable String username) {
        return ResponseEntity.ok(userService.delete(username));
    }
    @GetMapping("/{username}/avatar")
    public void getAvatar(@PathVariable String username, HttpServletResponse response) {
        //String aP = "/Users/park/Desktop/upload/avatar";
        String filePath = avatarPath + "/" + username + ".png";
        log.info("avatar Path :  " + filePath);
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
    @PutMapping("/{username}/changepassword")
    public ResponseEntity<?> changePassword(
            @PathVariable String username,
            @RequestBody ChangePasswordDTO changePasswordDTO,
            Authentication authentication) {

        String authenticatedUsername = authentication.getName();

        // 인증된 사용자와 요청된 사용자가 동일한지 확인
        if (!authenticatedUsername.equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can only change your own password.");
        }

        try {
            userService.changePassword(username, changePasswordDTO);
            return ResponseEntity.ok().body("Password changed successfully.");
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }

}


