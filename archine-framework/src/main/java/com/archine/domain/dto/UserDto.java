package com.archine.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String userName;
    private String phoneNumber;
    private String status;
    private String nickName;
    private String password;
    private String email;
    private String sex;
    private List<Long> roleIds;

}
