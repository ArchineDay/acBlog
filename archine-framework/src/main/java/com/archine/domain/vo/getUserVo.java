package com.archine.domain.vo;

import com.archine.domain.entity.Role;
import com.archine.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class getUserVo {
    List<Long> roleIds;
    List<Role> roles;
    User user;
}
