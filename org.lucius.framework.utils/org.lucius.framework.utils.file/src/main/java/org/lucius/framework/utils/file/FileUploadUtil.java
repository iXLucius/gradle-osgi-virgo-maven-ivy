/**
 * @(#)PicUploadUtil.java 1.0 2013-8-9
 * @Copyright:  Copyright 2000 - 2013 Isoftstone Tech. Co. Ltd. All Rights Reserved.
 * @Description: 
 * 
 * Modification History:
 * Date:        2013-8-9
 * Author:      Zhongxian Zhang 42343
 * Version:     MPRSP_CMS V1.D1.0.0.0
 * Description: (Initialize)
 * Reviewer:    
 * Review Date: 
 */
package org.lucius.framework.utils.file;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 图片批量上传工具类
 * Copyright:   Copyright 2000 - 2013 Isoftstone Tech. Co. Ltd. All Rights Reserved.
 * Date:        2013-8-9 下午3:39:20
 * Author:      Zhongxian Zhang 42343
 * Version:     MPRSP_CMS V1.D1.0.0.0
 * Description: Initialize
 */
public class FileUploadUtil
{
    /**图片格式正则表达式*/
    private static Pattern picPattern = Pattern.compile("(\\S+.(bmp|jpg|tiff|gif|pcx|tga|exif|fpx|svg|psd|cdr|pcd|dxf|ufo|eps|ai|raw)+$)");
    
    /**MPR格式正则表达式*/
    private static Pattern mprPattern = Pattern.compile("(\\S+.(mpr)+$)");
   
    /**保存正确的图片文件集合*/
    private ArrayList<String> successFileList = null;
   
    /**保存其他文件集合*/
    private ArrayList<String> otherFileList = null;
    
    /**
     * 批量读取文件夹中的图片，包括格式正确和不正确的图片
     * @param inputDirectoryPath
     * @return
     */
    public Map<String,List<String>> picBatchHandle(String inputDirectoryPath){
        Map<String,List<String>> map=new HashMap<String, List<String>>();
        successFileList = new ArrayList<String>();
        otherFileList = new ArrayList<String>();
        refreshPicFileList(inputDirectoryPath);
        map.put("successFileList", successFileList);
        map.put("otherFileList", otherFileList);
        return map;
    }
    
    /**
     * 批量读取文件夹中的图片，包括格式正确和不正确的图片
     * @param inputDirectoryPath
     * @return
     */
    public Map<String,List<String>> mprBatchHandle(String inputDirectoryPath){
        Map<String,List<String>> map=new HashMap<String, List<String>>();
        successFileList = new ArrayList<String>();
        otherFileList = new ArrayList<String>();
        refreshMprFileList(inputDirectoryPath);
        map.put("successFileList", successFileList);
        map.put("otherFileList", otherFileList);
        return map;
    }
    
    /**
     * 迭代读取MPR文件
     * 
     * @param inputDirectoryPath
     */
    private void refreshMprFileList(String inputDirectoryPath) { 
        File dir = new File(inputDirectoryPath); 
        File[] files = dir.listFiles(); 
        if (files == null) 
            return; 
        for (int i = 0; i < files.length; i++) { 
            if (files[i].isDirectory()) { 
                refreshPicFileList(files[i].getAbsolutePath()); 
            } else {
                Matcher match = mprPattern.matcher(files[i].getAbsolutePath().replaceAll(" ","").toLowerCase());
                if (match.matches())
                {
                    successFileList.add(files[i].getAbsolutePath());
                }
                else{
                    otherFileList.add(files[i].getAbsolutePath());
                }
            } 
        } 
    }
    
    /**
     * 迭代读取图片文件
     * 
     * @param inputDirectoryPath
     */
    private void refreshPicFileList(String inputDirectoryPath) { 
        File dir = new File(inputDirectoryPath); 
        File[] files = dir.listFiles(); 
        if (files == null) 
            return; 
        for (int i = 0; i < files.length; i++) { 
            if (files[i].isDirectory()) { 
                refreshPicFileList(files[i].getAbsolutePath()); 
            } else {
                Matcher match = picPattern.matcher(files[i].getAbsolutePath().replaceAll(" ","").toLowerCase());
                if (match.matches())
                {
                    successFileList.add(files[i].getAbsolutePath());
                }
                else{
                    otherFileList.add(files[i].getAbsolutePath());
                }
            } 
        } 
    }
    
    public static void main(String[] args)
    {
        FileUploadUtil picUploadUtil=new FileUploadUtil();
        Map<String,List<String>> map=picUploadUtil.picBatchHandle("c:/temp/pics");
        List<String> successFileList=map.get("successFileList");
        List<String> otherFileList=map.get("otherFileList");
        
        for(String filePath : successFileList){
            System.out.println("file:"+filePath);
        }
        System.out.println("--------------------------");
        for(String filePath2 : otherFileList){
            System.out.println("file:"+filePath2);
        }
    }
}

