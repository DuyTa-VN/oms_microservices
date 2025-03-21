package vn.duyta.userservice.repository;

import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.duyta.userservice.dto.identity.TokenExchangeParam;
import vn.duyta.userservice.dto.identity.TokenExchangeResponse;
import vn.duyta.userservice.dto.identity.UserCreationParam;

@FeignClient(name = "identity-client", url = "${idp.url}")
public interface IdentityClient {
    @PostMapping(value = "/realms/oms/protocol/openid-connect/token",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    TokenExchangeResponse exchangeToken(@QueryMap TokenExchangeParam param);

    @PostMapping(value = "/admin/realms/oms/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> createUser(
            @RequestHeader("Authorization") String token,
            @RequestBody UserCreationParam param);

    @DeleteMapping(value = "/admin/realms/oms/users/{keycloakUserId}")
    void deleteUser(
            @RequestHeader("Authorization") String token,
            @PathVariable("keycloakUserId") String keycloakUserId
    );

}
