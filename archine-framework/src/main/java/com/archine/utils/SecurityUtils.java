package com.archine.utils;


import com.archine.domain.entity.LoginUser;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityUtils
{

    /**
     * 获取用户
     **/
    public static LoginUser getLoginUser()
    {
        return (LoginUser) getAuthentication().getPrincipal();
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {

        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static Boolean isAdmin(){
        Long id = getLoginUser().getUser().getId();
        return id != null &&  id.equals(1L);
    }

    public static Long getUserId() {
        Long userId;
        try {
            userId = getLoginUser().getUser().getId();
        } catch (Exception e) {
//            e.printStackTrace();
            userId = -1L; // 表示是自己创建
        }
        return userId;
    }

}
