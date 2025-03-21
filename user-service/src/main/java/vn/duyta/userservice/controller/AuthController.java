package vn.duyta.userservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.duyta.userservice.dto.ApiResponse;
import vn.duyta.userservice.dto.request.LoginRequest;
import vn.duyta.userservice.dto.response.LoginResponse;
import vn.duyta.userservice.repository.IdentityClient;
import vn.duyta.userservice.service.AuthService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        return ApiResponse.<LoginResponse>builder()
                .result(this.authService.login(loginRequest))
                .build();
    }


}
