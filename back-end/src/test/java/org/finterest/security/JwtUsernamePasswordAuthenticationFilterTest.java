package org.finterest.security;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.finterest.security.account.dto.LoginDTO;
import org.finterest.security.filter.JwtUsernamePasswordAuthenticationFilter;
import org.finterest.security.handler.LoginFailureHandler;
import org.finterest.security.handler.LoginSuccessHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class JwtUsernamePasswordAuthenticationFilterTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private LoginSuccessHandler loginSuccessHandler;

    @Mock
    private LoginFailureHandler loginFailureHandler;

    @InjectMocks
    private JwtUsernamePasswordAuthenticationFilter jwtUsernamePasswordAuthenticationFilter;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = standaloneSetup(jwtUsernamePasswordAuthenticationFilter)
                .addFilter(jwtUsernamePasswordAuthenticationFilter, "/api/auth/login") // 필터를 MockMvc에 직접 추가
                .build();
    }

    @Test
    public void testSuccessfulAuthentication() throws Exception {
        // Mock LoginDTO
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("subin");
        loginDTO.setPassword("123456");

        // Mock Authentication
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());

        // Mock AuthenticationManager to return authentication
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);

        // Perform login request
        mockMvc.perform(post("/api/auth/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk());

        // Verify that loginSuccessHandler was called
        verify(loginSuccessHandler, times(1)).onAuthenticationSuccess(any(HttpServletRequest.class), any(HttpServletResponse.class), eq(authentication));
    }

    @Test
    public void testFailedAuthentication() throws Exception {
        // Mock LoginDTO
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("user123");
        loginDTO.setPassword("wrongPassword");

        // Mock AuthenticationException
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new AuthenticationException("Bad credentials") {});

        // Perform login request
        mockMvc.perform(post("/api/auth/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isUnauthorized());

        // Verify that loginFailureHandler was called
        verify(loginFailureHandler, times(1)).onAuthenticationFailure(any(HttpServletRequest.class), any(HttpServletResponse.class), any(AuthenticationException.class));
    }
}
