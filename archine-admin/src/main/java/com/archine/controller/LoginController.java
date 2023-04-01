package com.archine.controller;

import com.archine.domain.ResponseResult;
import com.archine.domain.entity.LoginUser;
import com.archine.domain.entity.Menu;
import com.archine.domain.entity.User;
import com.archine.domain.vo.AdminUserInfoVo;
import com.archine.domain.vo.MenuVo;
import com.archine.domain.vo.RoutersVo;
import com.archine.domain.vo.UserInfoVo;
import com.archine.enums.AppHttpCodeEnum;
import com.archine.exception.SystemException;
import com.archine.service.LoginService;
import com.archine.service.MenuService;
import com.archine.service.RoleService;
import com.archine.utils.BeanCopyUtils;
import com.archine.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;
import java.util.List;

@RestController
public  class LoginController {
    @Autowired
    private LoginService LoginService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    @PostMapping("/user/login")
    public ResponseResult AdminLogin(@RequestBody User user) {
        if (!StringUtils.hasText(user.getUserName())){
            //提示必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return LoginService.login(user);
    }

    @GetMapping("getInfo")
    //q:为什么这里要用ResponseResult<AdminUserInfoVo>而不是ResponseResult?
    //a:因为这里要返回的是一个对象，而不是一个字符串，所以要用泛型
    public ResponseResult<AdminUserInfoVo> getInfo(){
        //获取当前登录用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //根据用户id查询权限信息
        List<String> perms= menuService.selectPermsByUserId(loginUser.getUser().getId());
        //根据用户id查询角色信息
        List<String> roleKeyList= roleService.selectRoleKeyByUserId(loginUser.getUser().getId());
        //获取用户信息
        User user = loginUser.getUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);

        //封装返回数据
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms,roleKeyList,userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);

    }
    @GetMapping("getRouters")
    public ResponseResult<RoutersVo> getRouters(){
        Long userId = SecurityUtils.getUserId();
        //查询Menu 结果时tree的形式
        List<MenuVo> menus= menuService.selectRouterMenuTreeByUserId(userId);
        //封装数据返回
        return ResponseResult.okResult(new RoutersVo(menus));
    }


}
