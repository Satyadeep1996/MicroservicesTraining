package com.zensar.olxlogin.service.impl;

import com.zensar.olxlogin.dao.UserDao;
import com.zensar.olxlogin.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserDao userDao;

//    @Override
//    public UserDetails loadUserByUserName(String username) throws UsernameNotFoundException {
//        List<User> userList = userDao.findByUserName(username);
//        if (userList== null || userList.size() == 0)
//            throw new UsernameNotFoundException(username);
//        User userEntity = userList.get(0);
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("Admin"));
//        org.springframework.security.core.userdetails.User user
//                = new org.springframework.security.core.userdetails.User(userEntity.getUserName(),userEntity.getPassword(),authorities);
//        return user;
//    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<User> userList = userDao.findByUserName(s);
        if (userList== null || userList.size() == 0)
            throw new UsernameNotFoundException(s);
        User userEntity = userList.get(0);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("Admin"));
        org.springframework.security.core.userdetails.User user
                = new org.springframework.security.core.userdetails.User(userEntity.getUserName(),userEntity.getPassword(),authorities);
        return user;
    }

//    @Override
//    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        return null;
//    }
}
