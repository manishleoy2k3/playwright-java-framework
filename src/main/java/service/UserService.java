package service;

import builder.RequestBuilder;
import io.restassured.response.Response;
import model.CreateUserRequest;

public class UserService {

    private final RequestBuilder builder;

    public UserService(RequestBuilder builder) {
        this.builder = builder;
    }

    public Response getUsers(int page) {
        return builder.build()
                .get("/api/users?page=" + page);
    }

    public Response updateUser(int userId, CreateUserRequest user) {
        return builder.setBody(user)
                .build()
                .put("/api/users/" + userId);
    }

    public Response deleteUser(int userId) {
        return builder.build()
                .delete("/api/users/" + userId);
    }

    public Response getUserById(int userId) {
        return builder.build()
                .get("/api/users/" + userId);
    }
}