package org.finterest.user;
import org.finterest.user.dto.UserDTO;
import org.finterest.user.mapper.UserMapper;
import org.finterest.user.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
class UserServiceImplTest {

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserIdByUsername() {
        // Given
        String username = "subin";
        int expectedUserId = 14;
        UserDTO userDTO = userService.get(username);

        // Act: 실제 UserServiceImpl의 getUserIdByUsername을 호출
        //int actualUserId = userService.getUserIdByUsername(username);
        int actualUserId = userDTO.toVO().getUserId();
        System.out.println(actualUserId);
        System.out.println(expectedUserId);

    }
}