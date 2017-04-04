/**
 * @(#)SystemConstants.java 1.0 2015-5-9
 * @Copyright:  Copyright 2007 - 2015 MPR Tech. Co. Ltd. All Rights Reserved.
 * @Description: 
 * 
 * Modification History:
 * Date:        2015-5-9
 * Author:      Lucius lv
 * Version:     1.0.0.0
 * Description: (Initialize)
 * Reviewer:    
 * Review Date: 
 */
package org.lucius.framework.model;

public enum SystemConstants {
    
    //全局
    SUCCESS("01000000", "成功"),
    SYSTEM_IDENITY_AUTHENTICATION_FAILURE("01000001","系统认证失败"),
    SYSTEM_REQUEST_PROTOCOL_NOT_MATCH("01111112","请求协议不匹配"),
    FAILURE("01111111","系统异常"),
    
    //用户注册状态码
    REQUEST_PARAMETER_ERROR("01020101","请求参数值超限"),
    REGISTER_EMAIL_FORMAT_ERROR("01020102","邮箱格式错误"),
    REGISTER_USERNAME_FORMAT_ERROR("01020107","用户名格式错误"),
    REGISTER_PHONE_FORMAT_ERROR("01020108","电话号码格式错误"),
    USERNAME_HAS_EXIST("01020104","用户名已经存在"),
    EMAIL_HAS_REGISTER("01020105","邮箱已经被注册"),
    PHONE_NUMBER_HAS_REGISTER("01020106","电话号码已经被注册"),
    
    //用户登录
    LOGIN_USERNAME_NOT_EXIST("01020201","账户不存在"),
    LOGIN_PASSWORD_ERROR("01020202","密码不正确"),
    LOGIN_ACCOUNT_HAS_LOCKED("01020203","账户被锁定"),
    LOGIN_ACCOUNT_HAS_FROZEN("01020204","账户被冻结"),
    LOGIN_ACCOUNT_NOT_ACTIVE("01020205","账户未激活"),
    LOGIN_ACCOUNT_HAS_ACTIVED("01020206","账户已激活"),
    LOGIN_TERMINAL_HAS_BINDING("01020207","该终端已经绑定其他账户"),
    LOGIN_TERMINAL_LOSSED("01020208","终端被挂失"),
    LOGIN_USER_TYPE_ERROR("01020209","登录用户类型不匹配"),
    ACCOUNT_NOT_LOGIN("01020210","账户未登录系统"),
    ACCOUNT_UNBIND_ANY_TERMINAL("01020211","账户未绑定任何设备"),
    ACCOUNT_TERMINAL_HAS_REPORT_LOSS("01020212","账户未绑定任何设备"),
    
    //用户TICKET
    NO_THIS_TICKET_ERROR("01020500","缓存中无此Ticket"),
    TICKET_HAS_EXPIRED("01020501","Ticket已经失效"),
    TICKET_NOT_CORRECT("01020502","登录信息Ticket不正确"),
    
    //设备TICKET
    TERMINAL_TICKET_PARAMETER_IS_NULL("01040001","设备Ticket参数为空"),
    TERMINAL_TICKET_TERMINALID_OR_ACCOUNTID_NULL("01040002","Ticket中终端ID或者账户ID为空"),
    TERMINAL_TICKET_LENGTH_ERROR("01040003","Ticket长度不正确"),
    TERMINAL_TICKET_NOT_EXISTS("01040004","不存在此Ticket"),
    TERMINAL_TICKET_HAS_TIMEOUT("01040005","该Ticket已经过期"),
    TERMINAL_TICKET_EXTRATICKET_NONEXISTENT("01040006","附ticket不存在或已失效"),
    TERMINAL_TICKET_SYSTEM_DATA_EXCEPTION("01040007","Ticket数据异常"),
    TERMINAL_TICKET_PARAMETER_ERROR("01040008","Ticket参数错误"),
    
    TERMINAL_ID_IS_NULL("01050001","设备id参数为空"),
    TERMINAL_ID_INVALID("01050002","设备id参数不合法"),
    TERMINAL_STATUS_EXCEPTION("01050003","设备状态异常"),
    TERMINAL_REPORT_LOSS("01050004","设备已经挂失"),
    TERMINAL_NONEXISTENT("01050005","设备不存在"),
    
    CHANLENGE_PARAMETER_ERROR("01060001","Chanlenge参数错误"),
    CHANLENGE_LENGTH_ERROR("01040002","Chanlenge参数长度不正确"),
    
    
    AUTH_PASSCHANLENGE_ILLEGALPARAM("01070001","设备UID|数据包|结果包不合法"),
    AUTH_PASSCHANLENGE_FAIL("01070002","验签失败"),
    AUTH_PASSCHANLENGE_EXCEPTION("01070003","密管服务器异常"),
    
    AUTH_KMP_EXCEPTION("01070004","KMP服务器异常"),
    
    //激活码
    INVALID_ACTIVATION_CODE("01020600","无效的激活码"),
    ACTIVATION_CODE_HAS_EXPIRED("01020601","激活码已经过期"),
    ACTIVATION_CODE_NOT_EXIST("01020602","激活码不存在"),
    ACTIVATION_CODE_INCORRECT("01020603","激活码不正确"),
    ACTIVATION_CODE_HAS_USED("01020604","激活码已经被使用"),
    
    ACCOUNT_VALIDATE_CODE_NOT_EXIST("01010130","验证码不存在"),
    
    ACCOUNT_VALIDATE_CODE_NOT_VALID("01010133","验证码无效"),
    
    ACCOUNT_VALIDATE_CODE_NOT_CORRECT("01010131","验证码不正确"),
    
    ACCOUNT_VALIDATE_CODE_HAS_EXPIRED("01010132","验证码已过期"),
    
    //SPA相关
    SPA_NOT_HAS_ID("01030101","SPA没有有效的ID"),
    
    SPA_NOT_EXIST("01030102","不存在此SPA"),
    
    //################################################//
    
    USER_IS_NOT_EXIST("01000010", "用户不存在"),

    MANAGER_OLD_PASSWORD_NOT_CORRECT("01010010", "管理员原始密码不正确"),

    MANAGER_DB_QUERY_ERROR("01010001", "管理员用户查询失败"),

    MANAGER_DB_INSERT_ERROR("01010002", "管理员用户新增失败"),

    MANAGER_DB_UPDATE_ERROR("01010003", "管理员用户更新失败"),

    MANAGER_DB_DELETE_ERROR("01010004", "管理员用户删除失败"),

    ACCOUNT_OLD_PASSWORD_NOT_CORRECT("01010110", "原始密码不正确"),
    
    ACCOUNT_DB_QUERY_ERROR("01010101", "统一账户查询失败"),

    ACCOUNT_DB_INSERT_ERROR("01010102", "统一账户新增失败"),

    ACCOUNT_DB_UPDATE_ERROR("01010103", "统一账户更新失败"),

    ACCOUNT_DB_DELETE_ERROR("01010104", "统一账户删除失败"),
    
    ACCOUNT_NOT_EXIST("01010120", "统一账户不存在"),
    
    ACCOUNT_MODIFY_PASSWORD_ERROR("01010124", "统一账户修改密码错误"),
    
    ACCOUNT_PASSWORD_NOT_CORRECT("01010121", "统一账户密码不正确"),
    
    ACCOUNT_LOGIN_EXCEPTION("01010123", "统一账户密码不正确"),
    
    ACCOUNT_LOGIN_SUCCESS("01010122", "统一账户登录成功"),
    
    ROLE_NAME_CANNOT_EMPTY("01030001", "角色名称不能为空"),
    ROLE_NAME_ALREADY_EXIST("01030002", "角色名称已经存在"),
    ROLE_ADMIN_NO_MODIFY("01030003", "系统管理员不允许进行任何修改操作"),
    ROLE_IS_USED_NO_DELETE("01030004", "该角色含有帐户,不能删除");
    
    private String statusCode;
    private String message;

    private SystemConstants(String statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 根据状态码获取到相应的枚举
     * @param code 状态码
     * @return
     */
    public static SystemConstants getConstant(String code) {
        for (SystemConstants constants : SystemConstants.values()) {
            if (constants.getStatusCode().equals(code)) {
                return constants;
            }
        }
        return null;
    }

    /**
     * 根据状态码获取到相应的massage
     * @param code 状态码
     * @return
     */
    public static String getMessage(String code) {
        SystemConstants constants = getConstant(code);
        if (constants != null) {
            return constants.getMessage();
        }
        return null;
    }
}
