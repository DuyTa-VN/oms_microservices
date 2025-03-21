package vn.duyta.userservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private String expiresIn;
}
