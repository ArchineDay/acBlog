package com.archine.enums;

import com.archine.domain.entity.Menu;

public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200,"操作成功"),
    // 登录
    NEED_LOGIN(401,"需要登录后操作"),
    NO_OPERATOR_AUTH(403,"无权限操作"),
    SYSTEM_ERROR(500,"出现错误"),
    USERNAME_EXIST(501,"用户名已存在"),
     PHONENUMBER_EXIST(502,"手机号已存在"),
    EMAIL_EXIST(503, "邮箱已存在"),
    REQUIRE_USERNAME(504, "必需填写用户名"),
    LOGIN_ERROR(505,"用户名或密码错误"),
    CONTENT_NOT_NULL(506,"评论内容不能为空" ),
    FILE_TYPE_ERROR(507, "文件类型错误，请上传png或jpg或jpeg格式的文件"),
    USERNAME_NOT_NULL(508,"用户名不能为空" ),
    NICKNAME_NOT_NULL(509,"昵称不能为空" ),
    PASSWORD_NOT_NULL(510,"用密码不能为空" ),
    EMAIL_NOT_NULL(511,"邮箱不能为空" ), NICKNAME_EXIST(512,"昵称已存在" ),
    TAG_EXIST(512,"标签已存在" ),
    PARENT_MENU_ERROR(513,"修改菜单失败，上级菜单不能选择自己" ), HAS_CHILDREN_MENU_ERROR(514,"存在子菜单不允许删除" ),
    PHONE_EXIST(515,"手机号已存在" ),
    CANNOT_DELETE_SELF(516, "不能删除自己" ),
    DATA_NOT_EXIST(517,"数据不存在" );
    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}