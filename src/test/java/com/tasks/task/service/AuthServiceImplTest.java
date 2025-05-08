package com.tasks.task.service;

import com.tasks.task.config.JwtUtil;
import com.tasks.task.model.TokenResponse;
import com.tasks.task.model.User;
import com.tasks.task.model.UserDto;
import com.tasks.task.repository.AuthRepository;
import com.tasks.task.service.exceptions.RequestFormatInvalid;
import com.tasks.task.service.exceptions.UserAlreadyExists;
import com.tasks.task.service.exceptions.UserNotFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private AuthRepository authRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void loginUser_successful() {
        UserDto input = new UserDto("testuser", "password123");
        User user = new User(1L, "testuser", "hashedPassword");

        when(authRepository.findByUsername("testuser")).thenReturn(user);
        when(passwordEncoder.matches("password123", "hashedPassword")).thenReturn(true);
        when(jwtUtil.generateToken("testuser")).thenReturn("mocked-token");

        TokenResponse response = authService.loginUser(input);

        assertNotNull(response);
        assertEquals("mocked-token", response.getAccessToken());
    }

    @Test
    void loginUser_userNotFound() {
        UserDto input = new UserDto("unknown", "password");

        when(authRepository.findByUsername("unknown")).thenReturn(null);

        assertThrows(UserNotFound.class, () -> authService.loginUser(input));
    }

    @Test
    void loginUser_invalidPassword() {
        UserDto input = new UserDto("testuser", "wrongpass");
        User user = new User(1L, "testuser", "correctHashedPass");

        when(authRepository.findByUsername("testuser")).thenReturn(user);
        when(passwordEncoder.matches("wrongpass", "correctHashedPass")).thenReturn(false);

        assertThrows(RequestFormatInvalid.class, () -> authService.loginUser(input));
    }

    @Test
    void registerUser_successful() {
        UserDto input = new UserDto("newuser", "pass123");

        when(authRepository.existsByUsername("newuser")).thenReturn(false);
        when(passwordEncoder.encode("pass123")).thenReturn("encodedPassword");

        authService.registerUser(input);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(authRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertEquals("newuser", savedUser.getUsername());
        assertEquals("encodedPassword", savedUser.getPassword());
    }



    @Test
    void registerUser_userAlreadyExists() {
        UserDto input = new UserDto("existinguser", "pass123");

        when(authRepository.existsByUsername("existinguser")).thenReturn(true);

        assertThrows(UserAlreadyExists.class, () -> authService.registerUser(input));
    }
}
