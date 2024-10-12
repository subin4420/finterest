package org.finterest.user.controller;

import org.finterest.security.util.TokenUtil;
import org.finterest.user.dto.UserDTO;
import org.finterest.user.dto.UserJoinDTO;
import org.finterest.user.service.AdminUserService;
import org.finterest.user.service.UserService;
import org.finterest.user.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class AdminUserController {
    private final AdminUserService adminUserService;
    private final UserService userService;
    private final TokenUtil tokenUtil;  // TokenUtil 주입

    @Autowired
    public AdminUserController(AdminUserService adminUserService, UserService userService, TokenUtil tokenUtil) {
        this.adminUserService = adminUserService;
        this.userService = userService;
        this.tokenUtil = tokenUtil;  // TokenUtil 초기화
    }

    // 사용자 목록 조회
    @GetMapping("/api/admin/users")
    public ResponseEntity<List<UserVO>> getUsers() {
        List<UserVO> users = adminUserService.getUsers();
        return ResponseEntity.ok(users);
    }

    // 사용자 생성
    @PostMapping("/api/admin/users")
    public ResponseEntity<?> join(@RequestHeader("Authorization") String token,
                                  @ModelAttribute UserJoinDTO userJoinDTO) {
        // 관리자 권한 확인
        if (!tokenUtil.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "관리자 권한이 없습니다."));
        }

        // 사용자 정보 출력
        System.out.println("username :  " + userJoinDTO.getUsername());
        System.out.println("password :  " + userJoinDTO.getPassword());

        // 사용자 생성 로직 호출
        return ResponseEntity.ok(userService.join(userJoinDTO));
    }

    // 사용자 계정 상태 변경
    @PutMapping("/api/admin/users/{userId}/status")
    public ResponseEntity<?> updateUserStatus(@RequestHeader("Authorization") String token,
                                              @PathVariable int userId,
                                              @RequestBody Map<String, String> request) {
        // 관리자 권한 확인
        if (!tokenUtil.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "관리자 권한이 없습니다."));
        }

        // 요청에서 accountStatus 값 추출
        String newStatus = request.get("accountStatus");

        // 계정 상태 변경 로직 호출
        adminUserService.updateUserStatus(userId, newStatus);

        // 응답 메시지
        return ResponseEntity.ok(Map.of("message", "사용자 계정 상태가 변경되었습니다."));
    }

    // 사용자 삭제
    @DeleteMapping("/api/admin/users/{userId}")
    public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String token,
                                        @PathVariable int userId) {
        // 관리자 권한 확인
        if (!tokenUtil.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "관리자 권한이 없습니다."));
        }

        // 사용자 삭제 로직 호출
        adminUserService.deleteUser(userId);

        return ResponseEntity.ok(Map.of("message", "사용자가 삭제되었습니다."));
    }

}
