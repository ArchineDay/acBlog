package com.archine.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDto {
    private Long id;
    private String userName;
    private String status;
    private String nickName;
    private String email;
    private String phoneNumber;
    private String sex;
    private List<Long> roleIds;

}
