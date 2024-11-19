package com.thesearch.mylaptopshop.service.user;

import com.thesearch.mylaptopshop.dto.UserDto;
import com.thesearch.mylaptopshop.model.User;
import com.thesearch.mylaptopshop.request.CreateUserRequest;
import com.thesearch.mylaptopshop.request.UpdateUserRequest;

public interface IUserService {
    User getUserById(Long userId);
    User createUser(CreateUserRequest request); 
    User updateUser(UpdateUserRequest request, Long userId);
    void deleteUser(Long userId);
    UserDto convertUserToDto(User user);
    User getAuthenticatedUser();
}
