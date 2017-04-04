/**
 * @(#)FileUpload2GTPUtils.java 1.0 2016-1-14
 * @Copyright:  Copyright 2007 - 2016 MPR Tech. Co. Ltd. All Rights Reserved.
 * @Description: 
 * 
 * Modification History:
 * Date:        2016-1-14
 * Author:      sunsz
 * Version:     1.0.0.0
 * Description: (Initialize)
 * Reviewer:    
 * Review Date: 
 */
package org.lucius.framework.utils.file;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.lucius.framework.utils.http.HttpsClientUtil;
import org.lucius.framework.utils.http.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUpload2GTPUtils {
    
    static Logger logger = LoggerFactory.getLogger(FileUpload2GTPUtils.class);
    
    private static Map<String,Object> applySessionId(String isli_target_upload_url,String zipFilePath) throws IOException{
        
        File zipFile = new File(zipFilePath);
        String file_name = zipFile.getName();
        Long file_size = zipFile.length();
        String file_md5 = MD5Util.getFileMD5String(zipFile);

        Map<String, Object> uploadParams = new HashMap<String, Object>();
        uploadParams.put("file_name", file_name);
        uploadParams.put("file_size", String.valueOf(file_size));
        uploadParams.put("file_md5", file_md5);
        uploadParams.put("user_id", String.valueOf(System.currentTimeMillis()));

        // 申请sessionId
        String json = HttpsClientUtil.sendJsonContentType(
                isli_target_upload_url, JsonUtils.toJson(uploadParams));
        uploadParams.put("json",json);
        logger.info("申请sessionId:" + json);
        
        return uploadParams;
    }
    
    
    public static void upload2GTP(String isli_target_upload_url,String zipFilePath) throws NumberFormatException, JSONException, Exception{
        // 申请sessionId
        Map<String, Object> params = FileUpload2GTPUtils.applySessionId(isli_target_upload_url, zipFilePath);
        
        org.json.JSONObject usJson = new org.json.JSONObject(params.get("json").toString());

        if (org.apache.commons.lang3.StringUtils.equals(
                usJson.getString("return_code"), "0")) {
            org.json.JSONObject statusJson = usJson
                    .getJSONObject("status");
            String ru_session_id = statusJson
                    .getString("ru_session_id");
            String upload_url = statusJson.getString("upload_url");

            String target_url = upload_url + ru_session_id + "_"
                    + params.get("file_md5");
            
            //多线程上传
            MultiUpload mu = new MultiUpload();
            mu.beginToUpload(params,target_url,zipFilePath);
        }
        
    }
    
    /**
     * 处理完成删除临时文件
     * @param path
     * @param isli_target_upload_url
     * @param zipFilePath
     */
    public static void deleteFileAndDir(String path,String isli_target_upload_url,String zipFilePath){
        // 处理成功，退出循环并且执行删除临时文件操作
        try {
            FileUtil.delFile(zipFilePath);
        } catch (Exception e) {
            logger.error("删除zip文件失败", e);
        }
        try {
            FileUtil.delFolder(isli_target_upload_url);
        } catch (Exception e) {
            logger.error("删除媒体文件夹失败", e);
        }
        try {
            FileUtil.delFolder(path);
        } catch (Exception e) {
            logger.error("删除转码服务器生成文件夹失败", e);
        }
    }
    
    
    public static void main(String[] args) throws NumberFormatException, JSONException, Exception {

            //String file1 = "E:\\仓库\\软件\\1.iso";
           String file1 = "I:\\1.zip";

        upload2GTP("http://172.16.2.134:8080/api/isli_storage_system/file/uploadfile/put", file1);
        //System.out.println(Runtime.getRuntime().availableProcessors());
        //upload2GTP("http://172.16.2.134:8080/api/isli_storage_system/file/uploadfile/put", file2);
    }
}

