package vn.duyta.userservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vn.duyta.userservice.dto.ApiResponse;
import vn.duyta.userservice.dto.request.RegistrationRequest;
import vn.duyta.userservice.dto.response.UserResponse;
import vn.duyta.userservice.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    ApiResponse<UserResponse> register(@RequestBody @Valid RegistrationRequest request){
        return ApiResponse.<UserResponse>builder()
                .result(this.userService.register(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<UserResponse>> getAllUsers(){

        return ApiResponse.<List<UserResponse>>builder()
                .result(this.userService.getAllUsers())
                .build();
    }

    @GetMapping("/info")
    ApiResponse<UserResponse> getUserInfo(){
        return ApiResponse.<UserResponse>builder()
                .result(this.userService.getUser())
                .build();
    }

    @DeleteMapping("/delete/{keycloakUserId}")
    ApiResponse<Void> deleteUser(@PathVariable String keycloakUserId){
        this.userService.deleteUser(keycloakUserId);
        return ApiResponse.<Void>builder()
                .message("User deleted")
                .build();

    }

}
