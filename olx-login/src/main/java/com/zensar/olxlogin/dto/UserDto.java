package com.zensar.olxlogin.dto;

import com.zensar.olxlogin.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    int id;
    String firstName;
    String lastName;
    String userName;
    String password;
    String email;
    String phone;

    public UserDto(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.userName = user.getUserName();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.phone = user.getPhone();
    }
}
