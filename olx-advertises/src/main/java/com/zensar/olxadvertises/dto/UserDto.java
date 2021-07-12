package com.zensar.olxadvertises.dto;

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

}
