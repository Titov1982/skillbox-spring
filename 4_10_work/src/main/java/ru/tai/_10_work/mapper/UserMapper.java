package ru.tai._10_work.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.tai._10_work.model.User;
import ru.tai._10_work.web.model.UpsertUserRequest;
import ru.tai._10_work.web.model.UserListResponse;
import ru.tai._10_work.web.model.UserResponse;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {NewsMapper.class, CommentMapper.class})
public interface UserMapper {

    User requestToUser(UpsertUserRequest request);

    @Mapping(source = "userId", target = "id")
    User requestToUser(Long userId, UpsertUserRequest request);

    UserResponse userToResponse(User user);

    List<UserResponse> userListToResponseList(List<User> users);

    default UserListResponse userListToUserListResponse(List<User> users) {
        UserListResponse response = new UserListResponse();
        response.setUsers(userListToResponseList(users));
        return response;
    }
}
