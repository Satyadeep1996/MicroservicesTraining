package com.zensar.olxlogin.service.impl;

import com.zensar.olxlogin.dao.UserDao;
import com.zensar.olxlogin.dto.LoginDto;
import com.zensar.olxlogin.dto.UserDto;
import com.zensar.olxlogin.exception.InvalidUserException;
import com.zensar.olxlogin.model.User;
import com.zensar.olxlogin.service.UserService;
import com.zensar.olxlogin.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserDetailsService userDetailsService;


    @Override
    public ResponseEntity<String> authenticate(LoginDto loginDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUserName(), loginDto.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(loginDto.getUserName());
        }
        // Generate JWT token
        String jwtToken = jwtUtils.generateToken(loginDto.getUserName());
        return ResponseEntity.ok(jwtToken);
    }

    @Override
    public ResponseEntity<Boolean> logout(String authToken) {
        return null;
    }

    @Override
    public ResponseEntity<UserDto> createUser(UserDto userDto) {
        User user = new User(userDto);
        userDao.save(user);
        userDto.setId(user.getId());
        return ResponseEntity.ok(userDto);
    }

    @Override
    public ResponseEntity<UserDto> getUser(String authToken) {
        boolean isTokenValid = isTokenValidInternal(authToken);
        if (!isTokenValid) {
            throw new BadCredentialsException("Invalid Token");
        }

        Optional<User> user = userDao.findById(1);
        UserDto userDto = null;
        if (user.get() != null)
            userDto = new UserDto(user.get());
        else
            throw new InvalidUserException("Invalid User");

        return ResponseEntity.ok(userDto);
    }

    @Override
    public ResponseEntity<?> isTokenValid(String jwtToken) {
        jwtToken = jwtToken.substring(7, jwtToken.length());
        String userName = jwtUtils.extractUsername(jwtToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        boolean isTokenValid = jwtUtils.validateToken(jwtToken, userDetails);
        if (isTokenValid)
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        else
            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
    }

    public boolean isTokenValidInternal(String jwtToken) {
        jwtToken = jwtToken.substring(7, jwtToken.length());
        String userName = jwtUtils.extractUsername(jwtToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        boolean isTokenValid = jwtUtils.validateToken(jwtToken, userDetails);
        if (isTokenValid)
            return true;
        else
            return false;
    }


}
