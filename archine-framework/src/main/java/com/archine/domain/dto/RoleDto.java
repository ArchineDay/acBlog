package com.archine.domain.dto;

import com.archine.domain.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {

    private Long id;

    //角色名称
    private String roleName;

    //角色状态（0正常 1停用）
    private String status;

    //角色权限字符串
    private String roleKey;
    //显示顺序
    private Integer roleSort;
    //备注
    private String remark;

    private List<Long> menuIds;

}
