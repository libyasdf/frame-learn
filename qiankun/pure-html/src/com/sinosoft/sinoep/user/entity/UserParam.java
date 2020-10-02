package com.sinosoft.sinoep.user.entity;

/**
 * TODO 用户信息属性枚举类
 * @author 李利广
 * @Date 2019年03月05日 15:57:41
 */
public enum UserParam {

    /**
     * 用户ID
     */
    USER_ID("userid"),

    /**
     * 用户生日
     */
    BIRTHDAY("birthdate"),

    /**
     * 职务权限
     */
    POSITION_NAME("positionName"),

    /**
     * 职级
     */
    POSITION_LEVEL("positionLevel"),

    /**
     * 用户昵称
     */
    USER_NM("userNm"),

    /**
     * 涉密等级
     */
    SENSITIVE_MARK("sensitiveMark"),

    /**
     * 出入卡号
     */
    CARD_NUMBER("cardNumber"),

    /**
     * 性别
     */
    USER_SEX("userSex"),

    /**
     * 职务级别
     */
    POSITION_REAL_LEVEL("positionRealLevel"),

    /**
     * 警号
     */
    POLICE_NUM("policeNumber"),

    /**
     * 初始化密码
     */
    INIT_PWD("initpwd"),

    /**
     * 用户真实姓名
     */
    USER_NAME_FULL("usernamefull"),

    /**
     * 用户电话
     */
    PHONE("phone"),

    /**
     * 用户邮箱
     */
    USER_EMAIL("userEmail"),

    /**
     * CA证书号码
     */
    CA_G_CODE("caGCode"),

    /**
     * 身份证号
     */
    IDENTITY("identity"),

    /**
     * 用户CODE
     */
    USER_CODE("userCode"),

    /**
     * 用户名（登录名）
     */
    USER_NAME("username"),

    /**
     * 登录密码
     */
    USER_PWD("userPassword"),

    /**
     * 用户添加时间
     */
    TIME("time"),

    /**
     * 添加人
     */
    AUTHER("auther"),

    /**
     * 地址
     */
    ADDRESS("address"),

    /**
     * 公司
     */
    COMPANY("company"),

    /**
     * QQ号
     */
    QQ("qq"),

    /**
     * 微信unionid
     */
    UNION_ID("unionid"),

    /**
     * 用户邮箱密码
     */
    USER_EMAIL_PWD("userEmailPsw"),

    /**
     * 微信
     */
    WECHAT("wechat"),

    /**
     * 头像
     */
    IMAGE("imgContent"),

    /**
     * 用户状态
     */
    STATUS("status"),

    /**
     * 备注信息
     */
    NOTE("note");

    private String name;

    UserParam(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
