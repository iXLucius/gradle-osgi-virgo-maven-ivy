package org.lucius.framework.utils.encrypt;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
/**
 * 
 * 3DES加密工具类
 * 采用的密钥长度是56*3+8*3=192位 24个字节
 * @author  wuchaohua
 * @version  [版本号, 2013-11-15]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DESUtil {
    
    /** 定义 加密算法,可用 DES,DESede,Blowfish */ 
    private static final String ALGORITHM = "DESede";
      
    /**
     * 3DES加密.
     * @param keybyte 加密密钥，长度为24字节
     * @param src 被加密的数据缓冲区（源）
     * @return byte[] 解密后字节数据
     */
    public static byte[] encryptMode(byte[] keybyte, byte[] src) {  
        try {
            //生成密钥  
            SecretKey deskey = new SecretKeySpec(keybyte, ALGORITHM);  
  
            //加密  
            Cipher c1 = Cipher.getInstance(ALGORITHM);
            c1.init(Cipher.ENCRYPT_MODE, deskey);  
            return c1.doFinal(src);  
        } catch (java.security.NoSuchAlgorithmException e1) {  
            e1.printStackTrace();  
        } catch (javax.crypto.NoSuchPaddingException e2) {  
            e2.printStackTrace();  
        } catch (java.lang.Exception e3) {  
            e3.printStackTrace();  
        }
        return null;  
    }
  
    /**
     * 3DES解密.
     * @param keybyte 为加密密钥，长度为24字节  
     * @param src 为加密后的缓冲区
     * @return byte[] 解密后字节数据
     */
    public static byte[] decryptMode(byte[] keybyte, byte[] src) {        
        try {
            //生成密钥  
            SecretKey deskey = new SecretKeySpec(keybyte, ALGORITHM);  
            //解密  
            Cipher c1 = Cipher.getInstance(ALGORITHM);  
            c1.init(Cipher.DECRYPT_MODE, deskey);  
            return c1.doFinal(src);  
        } catch (java.security.NoSuchAlgorithmException e1) {  
            e1.printStackTrace();  
        } catch (javax.crypto.NoSuchPaddingException e2) {  
            e2.printStackTrace();  
        } catch (java.lang.Exception e3) {  
            e3.printStackTrace();  
        }  
        return null;  
    }  
  
    /**
     * 字节数组输出hex字符串，每个字节以空格分隔
     * @param data data
     */
    @SuppressWarnings("unused")
    private static String byte2hex(byte[] data){
        if(null == data){
            return "";
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            String hex = Integer.toHexString(data[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            buffer.append(hex.toUpperCase()+" ");
        }
        return buffer.toString();
    }
    
    /**
     * 测试函数
     * @param args args
     */
    @SuppressWarnings("unused")
    public static void main1(String[] args) { 
        //添加新安全算法,如果用JCE就要把它添加进去  
        //Security.addProvider(new com.sun.crypto.provider.SunJCE());  
        //24字节的密钥
        byte[] keyBytes = {0x11, 0x22, 0x4F, 0x58, (byte)0x88, 0x10, 0x40, 0x38  
                               , 0x28, 0x25, 0x79, 0x51, (byte)0xCB, (byte)0xDD, 0x55, 0x66  
                               , 0x77, 0x29, 0x74, (byte)0x98, 0x30, 0x40, 0x36, (byte)0xE2}; 
        
        //手机APP调用后台的getEncodeString()获取加密密钥
        //http://tspp.icarnet.cn/appcar/getEncodeString
        String key = "046f4cc6bc556ac0697ff563";//UUID.randomUUID().toString().substring(8).replace("-","");
        //System.out.println(key + ",key length="+key.length() + ",It's OK");
        keyBytes = key.getBytes();
        String szSrc = "111111";  
          
        //System.out.println("加密前的字符串:" + szSrc);  
          
        byte[] encoded = encryptMode(keyBytes, szSrc.getBytes());          
        //System.out.println("加密后的字符串:" + Base64Util.encode(encoded));  
        //System.out.println("加密后的字符串:" + byte2hex(encoded));  
        encoded = Base64Util.decode("QixH6+bLMUSUxfa3rl4ZfthTO5JxLyNKUBqcmtceKP4=");
        byte[] srcBytes = decryptMode(keyBytes, encoded);  
        //System.out.println("解密后的字符串:" + (new String(srcBytes)));  
    } 

}
