/**
 * @(#)MultiUpload.java 1.0 2016-1-14
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
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.lucius.framework.utils.http.HttpsClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultiUpload {

    Logger logger = LoggerFactory.getLogger(MultiUpload.class);

    private static final Long MAX_BLOCK = 8 * 1024 * 1024L;
    
    private Map<String,Object> threads = new ConcurrentHashMap<String,Object>();

    private Long fileSize;

    private String fileName;

    private String fileMd5;

    public void beginToUpload(Map<String, Object> params, String target_url,
            String filePath) throws IOException {

        fileSize = Long.parseLong(params.get("file_size").toString());
        fileName = params.get("file_name").toString();
        fileMd5 = params.get("file_md5").toString();
        File f = new File(filePath);
        FileInputStream fis = new FileInputStream(f);
        FileChannel fc = fis.getChannel();
        CountDownLatch threadSignal = null;
        ExecutorService executor = null;
        if (fileSize <= MAX_BLOCK) {
            executor = Executors.newFixedThreadPool(1);
            threadSignal = new CountDownLatch(1);//初始化countDown
            Runnable thread = new Mu(target_url,0,(fileSize - 1),fc,fileMd5,fileName,threadSignal);
            executor.submit(thread);
            
        } else {
            Long threadSize = fileSize / MAX_BLOCK + 1;
            Long startByte = 0L;
            executor = Executors.newFixedThreadPool(10);
            threadSignal = new CountDownLatch(threadSize.intValue());//初始化countDown
            // 将上传数据存储到数组中
            for (int i = 0; i < threadSize; i++) {
                Runnable thread = null;
                if (i == threadSize - 1) {
                    
                    thread = new Mu(target_url,startByte,(fileSize - 1),fc,null,startByte + "_" + (fileSize - 1)
                                    + ".tmp",threadSignal);
                } else {
                    thread = new Mu(target_url,startByte,(startByte+MAX_BLOCK - 1),fc,null,startByte + "_" + (startByte+MAX_BLOCK - 1)
                                    + ".tmp",threadSignal);
                }
                executor.submit(thread);
                
                startByte = startByte + MAX_BLOCK;
            }

        }
        
        try {
            //等待所有子线程执行完
            threadSignal.await();
            // 关闭线程池  
            executor.shutdown();  
            logger.info("all thread clear.");
            fis.close();
            fc.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        

    }
    
    
    class Handle implements Runnable{

        @Override
        public void run() {
            // TODO Auto-generated method stub
            
        }
        
    }

    class Mu implements Runnable {

        private String target_url = "";
        private String fileName = "";
        private long startPos = 0L;
        private long endPos = 0L;
        private FileChannel fc;
        private String fileMD5;
        private CountDownLatch threadsSignal;
        public Mu(String target_url,long startPos,long endPos,FileChannel fc,String fileMD5,String fileName,CountDownLatch threadsSignal) {
            super();
            this.target_url = target_url;
            this.startPos = startPos;
            this.endPos = endPos;
            this.fc = fc;
            this.fileMD5 = fileMD5;
            this.fileName = fileName;
            this.threadsSignal = threadsSignal;  
        }

        @Override
        public void run() {
            ByteBuffer buffer = null;
            try {
                Map<String, Object> uparams = new HashMap<String, Object>();
                uparams.put("part_name", fileName);
                uparams.put("start_byte",startPos+"");
                uparams.put("end_byte", endPos+"");
                uparams.put("part_md5", fileMD5);
                buffer = ByteBuffer.allocate((int) (endPos-startPos)+1);
                byte[] fb = new byte[(int) (endPos-startPos)+1];
                fc.read(buffer,startPos);
                buffer.flip();
                buffer.get(fb);
                if(fileMD5==null){
                    uparams.put("part_md5", getMD5String(fb));
                }
                
                
                threads.put(uparams.get("part_md5")+"","0");
                
                
                logger.info("分块上传开始执行..." + uparams.toString());
                
                threads.put(uparams.get("part_md5")+"",uparams);
                String uploadJsonStr = HttpsClientUtil.doMultipleRequest(
                        target_url, fb, uparams);

                if (uploadJsonStr == "") {
                    logger.error("上传错误，返回空");
                    return;
                }
                org.json.JSONObject uploadJson = new org.json.JSONObject(
                        uploadJsonStr);
                if (!org.apache.commons.lang3.StringUtils.equals(
                        uploadJson.getString("return_code"), "0")) {
                    logger.error("上传错误，原因：" + uploadJson.toString());
                    System.out.println("上传错误，原因：" + uploadJson.toString());
                    threads.put(uparams.get("part_md5")+"","error "+uparams);
                } else {
                    logger.info("上传成功：" + uparams + "\n"
                            + uploadJson.toString());
                    //System.out.println("上传成功：" + uparams + "\n"
                    //        + uploadJson.toString());
                    threads.remove(uparams.get("part_md5")+"");
                }
            } catch (JSONException e) {
                logger.error("媒体文件上传ISLI关联目标池失败：", e);
            } catch (Exception e) {
                logger.error("媒体文件上传ISLI关联目标池失败：", e);
                System.out.println(e+"");
            }finally{
                //System.out.println(threadsSignal);
                buffer.clear();
                threadsSignal.countDown();//线程结束时计数器减1
            }
            

        }

    }

    private static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    public String getMD5String(byte[] bytes) {
        MessageDigest messagedigest = null;
        try {
            messagedigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException nsaex) {
            System.err.println(MD5Util.class.getName()
                    + "初始化失败，MessageDigest不支持MD5Util。");
            nsaex.printStackTrace();
        }
        messagedigest.update(bytes);
        return bufferToHex(messagedigest.digest());
    }

    private String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 0xf0) >> 4];
        char c1 = hexDigits[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }
    public static void main(String[] args) throws IOException, InterruptedException {
            // 创建一个固定大小的线程池
            ExecutorService service = Executors.newFixedThreadPool(3);
            for (int i = 0; i < 10; i++) {
                System.out.println("创建线程" + i);
                Runnable run = new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("启动线程");
                    }
                };
                // 在未来某个时间执行给定的命令
                service.execute(run);
            }
            // 关闭启动线程
            service.shutdown();
            // 等待子线程结束，再继续执行下面的代码
            service.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
            System.out.println("all thread complete");
    }
}
