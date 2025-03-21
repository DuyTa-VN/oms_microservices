package vn.duyta.userservice.mapper;

import org.mapstruct.Mapper;
import vn.duyta.userservice.dto.request.RegistrationRequest;
import vn.duyta.userservice.dto.response.UserResponse;
import vn.duyta.userservice.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(RegistrationRequest request);
    UserResponse toUserResponse(User user);
}
