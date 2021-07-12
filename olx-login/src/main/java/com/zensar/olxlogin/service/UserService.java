package com.zensar.olxlogin.service;

import com.zensar.olxlogin.dto.LoginDto;
import com.zensar.olxlogin.dto.UserDto;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<String> authenticate(LoginDto loginDto);

    ResponseEntity<Boolean> logout(String authToken);

    ResponseEntity<UserDto> createUser(UserDto userDto);

    ResponseEntity<UserDto> getUser(String authToken);

    ResponseEntity<?> isTokenValid(String jwtToken);
}
