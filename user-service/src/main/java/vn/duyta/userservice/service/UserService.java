package vn.duyta.userservice.service;

import feign.FeignException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.duyta.userservice.dto.identity.Credential;
import vn.duyta.userservice.dto.identity.TokenExchangeParam;
import vn.duyta.userservice.dto.identity.UserCreationParam;
import vn.duyta.userservice.dto.request.RegistrationRequest;
import vn.duyta.userservice.dto.response.UserResponse;
import vn.duyta.userservice.exception.AppException;
import vn.duyta.userservice.exception.ErrorCode;
import vn.duyta.userservice.exception.ErrorNormalizer;
import vn.duyta.userservice.mapper.UserMapper;
import vn.duyta.userservice.repository.IdentityClient;
import vn.duyta.userservice.repository.UserRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final IdentityClient identityClient;
    private final ErrorNormalizer errorNormalizer;

    @Value("${idp.client_id}")
    @NonFinal
    private String clientId;

    @Value("${idp.client_secret}")
    @NonFinal
    private String clientSecret;

    public UserResponse register(@Valid RegistrationRequest request) {
        //create account in Keycloak
        try{
            //Exchange client token
            var token = identityClient.exchangeToken(TokenExchangeParam.builder()
                    .grant_type("client_credentials")
                    .client_id(clientId)
                    .client_secret(clientSecret)
                    .scope("openid")
                    .build());

            log.info("TokenInfo: {}", token);
            //create user with client token and given info
            var creationResponse = identityClient.createUser(
                    "Bearer " + token.getAccessToken(),
                    UserCreationParam.builder()
                            .username(request.getUsername())
                            .firstName(request.getFirstName())
                            .lastName(request.getLastName())
                            .email(request.getEmail())
                            .enabled(true)
                            .emailVerified(false)
                            .credentials(List.of(Credential.builder()
                                    .type("password")
                                    .temporary(false)
                                    .value(request.getPassword())
                                    .build()))
                            .build());


            String keycloakUserId = extractKeycloakUserId(creationResponse);
            log.info("KeycloakUserId: {}", keycloakUserId);
            //Get keycloakUserId of keycloak account


            var user = userMapper.toUser(request);
            user.setKeycloakUserId(keycloakUserId);
            user = userRepository.save(user);

            return userMapper.toUserResponse(user);
        }catch (FeignException exception){
            throw errorNormalizer.handleKeyCloakException(exception);
        }
    }

    private String extractKeycloakUserId(ResponseEntity<?> response) {
        String location = response.getHeaders().getFirst("Location");
        String[] splitedStr = location.split("/");
        return splitedStr[splitedStr.length - 1];
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUsers(){
        var users = userRepository.findAll();
        return users.stream().map(userMapper::toUserResponse).toList();
    }

    public UserResponse getUser(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String keycloakUserId = authentication.getName();

        var user = userRepository.findByKeycloakUserId(keycloakUserId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(String keycloakUserId){
        try{
            //Exchange client token
            var token = identityClient.exchangeToken(TokenExchangeParam.builder()
                    .grant_type("client_credentials")
                    .client_id(clientId)
                    .client_secret(clientSecret)
                    .scope("openid")
                    .build());
            log.info("TokenInfo: {}", token);


            identityClient.deleteUser("Bearer " + token.getAccessToken(), keycloakUserId);
            userRepository.deleteByKeycloakUserId(keycloakUserId);
        }catch (FeignException exception){
            throw errorNormalizer.handleKeyCloakException(exception);
        }
    }


}
