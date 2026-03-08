package com.diego.iatrainig.service;

import com.diego.iatrainig.dto.UserRequest;
import com.diego.iatrainig.dto.UserResponse;

import java.util.List;

public interface UserService {

    /** Creates a new user. Fails if the email is already registered. */
    UserResponse createUser(UserRequest request);

    /** Returns a user by id. */
    UserResponse getUserById(String id);

    /** Returns all users. */
    List<UserResponse> getAllUsers();

    /** Updates an existing user's data. */
    UserResponse updateUser(String id, UserRequest request);

    /** Deletes a user by id. */
    void deleteUser(String id);
}
