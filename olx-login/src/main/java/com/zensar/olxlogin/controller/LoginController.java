package com.zensar.olxlogin.controller;

import com.zensar.olxlogin.dto.LoginDto;
import com.zensar.olxlogin.dto.UserDto;
import com.zensar.olxlogin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    UserService userService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody LoginDto loginDto){
        return userService.authenticate(loginDto);
    }

    @GetMapping("/token/validate")
    public ResponseEntity<?> isTokenValid(@RequestHeader("Authorization") String jwtToken){
        return userService.isTokenValid(jwtToken);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("auth-token") String authToken){
        return userService.logout(authToken);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto){
        return userService.createUser(userDto);
    }

    @GetMapping
    public ResponseEntity<?> getUser(@RequestHeader("auth-token") String authToken){
        return userService.getUser(authToken);
    }

}
