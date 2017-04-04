package org.lucius.framework.utils.http;

public class Constant{
    
    /************************End 后台管理菜单权限相关参数值***************************/
    
    public static final String SYSTEM_SPA_ID = "6BF2F038-9A5B-4F6C-B47D-94944CBD9187";

    public static final String SYTEM_IDENTITY = "38F6412E-0951-4FB6-9AC9-7B253AB4627D";
    
    public static final String ENCRYPT_KEY = "ED540311-B79B-450D-A0D5-BFC5392BB2B7";
    
    /**
    * https证书
    */
    public static final String CertificateURL = System.getProperty("catalina.base")+"/configuration/keyStore/server.keystore";
    public static final String CertificatePass = "123456";
    
}
