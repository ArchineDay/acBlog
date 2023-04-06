package com.archine.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailVo {
        private Long id;
        //用户名
        private String userName;
        //昵称
        private String nickName;

        private String status;
        //邮箱
        private String email;
        //手机号
        private String phoneNumber;
        //用户性别（0男，1女，2未知）
        private String sex;
        //头像
        private String avatar;
        //创建时间
        private Date createTime;
        //更新人
        private Long updateBy;
        //更新时间
        private Date updateTime;

}
