package com.archine.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {

    private Long RoleId;

    //角色名称
    private String roleName;

    //角色状态（0正常 1停用）
    private String status;

}
