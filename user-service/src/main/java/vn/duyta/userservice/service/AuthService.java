package vn.duyta.userservice.service;

import feign.FeignException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import vn.duyta.userservice.dto.identity.TokenExchangeParam;
import vn.duyta.userservice.dto.request.LoginRequest;
import vn.duyta.userservice.dto.response.LoginResponse;
import vn.duyta.userservice.exception.ErrorNormalizer;
import vn.duyta.userservice.mapper.UserMapper;
import vn.duyta.userservice.model.User;
import vn.duyta.userservice.repository.IdentityClient;
import vn.duyta.userservice.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final IdentityClient identityClient;
    private final ErrorNormalizer errorNormalizer;

    @Value("${idp.client_id}")
    @NonFinal
    private String clientId;

    @Value("${idp.client_secret}")
    @NonFinal
    private String clientSecret;

    public LoginResponse login(@Valid LoginRequest loginRequest){
        try {
            var token = identityClient.exchangeToken(TokenExchangeParam.builder()
                    .grant_type("password")
                    .client_id(clientId)
                    .client_secret(clientSecret)
                    .username(loginRequest.getUsername())
                    .password(loginRequest.getPassword())
                    .scope("openid")
                    .build());

            log.info("TokenInfo: {}", token);


            return LoginResponse.builder()
                    .accessToken(token.getAccessToken())
                    .expiresIn(token.getExpiresIn())
                    .refreshToken(token.getRefreshToken())
                    .build();

        }catch (FeignException exception){
            throw errorNormalizer.handleKeyCloakException(exception);
        }
    }
}
