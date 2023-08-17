package com.teamdevAcademy.academy.services;
import com.teamdevAcademy.academy.dto.LoginDto;
import com.teamdevAcademy.academy.dto.UserDTO;
import com.teamdevAcademy.academy.entities.User;
import com.teamdevAcademy.academy.entities.UserRole;

import java.util.List;

public interface UserService {

    User Signup(UserDTO userDTO, UserRole role);

    List<User> getAllUsers();

    User getUserById(Long id);

    User updateUser(Long id, UserDTO user);

    boolean DeleteUserById(Long id);

    Long CountUser();

    User signIn(LoginDto loginDto);

    public User getUserByEmail(String email);
     User getUserByNtel(Integer Ntel);
    Long validateToken(String Token);

    public User verifyAccount( String verificationToken );

    boolean isSessionValid(User user);
}