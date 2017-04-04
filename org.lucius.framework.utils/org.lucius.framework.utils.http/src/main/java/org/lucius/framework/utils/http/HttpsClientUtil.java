package org.lucius.framework.utils.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.springframework.util.DigestUtils;

/**
 * 
 * Copyright:   Copyright 2007 - 2015 MPR Tech. Co. Ltd. All Rights Reserved.
 * Date:        2015-4-23 上午9:57:07
 * Author:      Lucius lv
 * Version:     1.0.0.0
 * Description: Initialize
 */
public class HttpsClientUtil {

    private static final int BUFFER_SIZE = 1024;
    
    private static final Log LOG = LogFactory.getLog(HttpsClientUtil.class);

    @SuppressWarnings("deprecation")
    private static String doGetRequest(HttpClient httpclient, String md5Sign,
            String url) throws IOException {
        HttpGet httpget = new HttpGet(url);

        httpget.setHeader("Connection", "close");
        httpget.setHeader("sysIdentity", Constant.SYTEM_IDENTITY);
        httpget.setHeader("sign", md5Sign);
        
        HttpResponse response = null;
        try {
            response = httpclient.execute(httpget);
        } catch (Exception e) {
            httpclient.getConnectionManager().closeExpiredConnections();
            httpclient.getConnectionManager().closeIdleConnections(0,
                    TimeUnit.SECONDS);
            response = httpclient.execute(httpget);
        }
        HttpEntity entity = response.getEntity();
        String result = null;
        if (entity != null) {
            result = toString(entity.getContent(), HTTP.UTF_8);
            entity.consumeContent();
        }
        return result;
    }

    public static String doGetRequest(String url, Map<String, String> params)
            throws IOException {

        Map<String, String> treeMap = new TreeMap<String, String>(
                new CollatorComparator());
        if (MapUtils.isNotEmpty(params)) {
            treeMap.putAll(params);
        }

        StringBuilder entryptString = new StringBuilder("");
        StringBuilder urlString = new StringBuilder(url);
        // 拼接所有的参数
        if (MapUtils.isNotEmpty(params)) {
            if (!StringUtils.contains(urlString, "?")) {
                urlString.append("?");
            }
        }

        for (Entry<String, String> entry : treeMap.entrySet()) {
            String str = entry.getKey() + "=" + entry.getValue();
            entryptString.append(str);
            if (StringUtils.endsWithIgnoreCase(urlString, "?")) {
                urlString.append(str).append("&");
            } else {
                urlString.append("&").append(str).append("&");
            }

        }

        if (StringUtils.endsWith(urlString.toString(), "&")) {
            urlString.deleteCharAt(urlString.length() - 1);
        }

        // 拼接系统密钥
        entryptString.append(Constant.ENCRYPT_KEY);

        // MD5加密
        String md5Sign = DigestUtils
                .md5DigestAsHex(entryptString.toString().getBytes("UTF-8"));
        HttpClient httpclient = HttpConnectionManager.getHTTPSClient();
        return doGetRequest(httpclient, md5Sign, urlString.toString());
    }

    /**
     * 发送PUT方式的远程请求
     * 
     * @param url
     *            请求地址
     * @param params
     *            参数
     * @return 返回处理结果
     * @throws UnsupportedEncodingException 
     */
    @SuppressWarnings("deprecation")
    public static String doPutRequest(String url, Map<String, String> params) throws UnsupportedEncodingException {
        HttpPut httpPut = new HttpPut(url);
        List<NameValuePair> nvps = setNameValuePair(params);
        httpPut.setHeader("Connection", "close");
        httpPut.setHeader("sysIdentity", Constant.SYTEM_IDENTITY);

        Map<String, String> treeMap = new TreeMap<String, String>(
                new CollatorComparator());
        if (MapUtils.isNotEmpty(params)) {
            treeMap.putAll(params);
        }

        StringBuilder entryptString = new StringBuilder("");
        for (Entry<String, String> entry : treeMap.entrySet()) {
            entryptString.append(entry.getKey() + "=" + entry.getValue());
        }
        // 拼接系统密钥
        entryptString.append(Constant.ENCRYPT_KEY);
        // MD5加密
        String md5Sign = DigestUtils
                .md5DigestAsHex(entryptString.toString().getBytes("UTF-8"));
        httpPut.setHeader("sign", md5Sign);

        HttpResponse response = null;
        String result = null;
        HttpClient httpclient = HttpConnectionManager.getHTTPSClient();
        try {
            httpPut.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
            response = httpclient.execute(httpPut);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                result = toString(entity.getContent(), HTTP.UTF_8);
                entity.consumeContent();
            }
        } catch (Exception e) {
            httpclient.getConnectionManager().closeExpiredConnections();
            httpclient.getConnectionManager().closeIdleConnections(0,
                    TimeUnit.SECONDS);
        }

        return result;
    }

    @SuppressWarnings("deprecation")
    public static String doMultipleRequest(String url, String filePath,
            Map<String, String> param) throws Exception {
        HttpPost httpost = new HttpPost(url);
        HttpClient httpclient = HttpConnectionManager.getHTTPSClient();
        httpost.setHeader("Connection", "close");

        httpost.setHeader("sysIdentity", Constant.SYTEM_IDENTITY);
        Map<String, String> treeMap = new TreeMap<String, String>(
                new CollatorComparator());
        if (MapUtils.isNotEmpty(param)) {
            treeMap.putAll(param);
        }
        StringBuilder entryptString = new StringBuilder("");
        for (Entry<String, String> entry : treeMap.entrySet()) {
            entryptString.append(entry.getKey() + "=" + entry.getValue());
        }
        // 拼接系统密钥
        entryptString.append(Constant.ENCRYPT_KEY);
        // MD5加密
        String md5Sign = DigestUtils
                .md5DigestAsHex(entryptString.toString().getBytes("UTF-8"));
        httpost.setHeader("sign", md5Sign);

        FileBody fileBody = new FileBody(new File(filePath));
        MultipartEntity multipartEntity = new MultipartEntity();
        multipartEntity.addPart("isliDataXml", fileBody);
        Iterator<String> itre = param.keySet().iterator();
        while (itre.hasNext()) {
            String paramKey = itre.next();
            StringBody stringBody = new StringBody(param.get(paramKey));
            multipartEntity.addPart(paramKey, stringBody);
        }
        httpost.setEntity(multipartEntity);
        HttpResponse response = null;
        try {
            response = httpclient.execute(httpost);
        } catch (Exception e) {
            httpclient.getConnectionManager().closeExpiredConnections();
            httpclient.getConnectionManager().closeIdleConnections(0,
                    TimeUnit.SECONDS);
            response = httpclient.execute(httpost);
        }
        HttpEntity entity = response.getEntity();
        String result = null;
        if (entity != null) {
            result = toString(entity.getContent(), HTTP.UTF_8);
            entity.consumeContent();
        }
        return result;
    }
    
    @SuppressWarnings("deprecation")
    public static String doMultipleRequest(String url, byte[] fb,
            Map<String, Object> param) throws Exception {
        HttpPost httpost = new HttpPost(url);
        HttpClient httpclient = HttpConnectionManager.getHTTPSClient();
        httpost.setHeader("Connection", "close");

        httpost.setHeader("sysIdentity", Constant.SYTEM_IDENTITY);
        Map<String, Object> treeMap = new TreeMap<String, Object>(
                new CollatorComparator());
        if (MapUtils.isNotEmpty(param)) {
            treeMap.putAll(param);
        }
        StringBuilder entryptString = new StringBuilder("");
        for (Entry<String, Object> entry : treeMap.entrySet()) {
            entryptString.append(entry.getKey() + "=" + entry.getValue());
        }
        // 拼接系统密钥
        entryptString.append(Constant.ENCRYPT_KEY);
        // MD5加密
        String md5Sign = DigestUtils
                .md5DigestAsHex(entryptString.toString().getBytes("UTF-8"));
        httpost.setHeader("sign", md5Sign);

        ByteArrayBody bABody = new ByteArrayBody(fb, "fileName");

        MultipartEntity multipartEntity = new MultipartEntity();
        Iterator<String> itre = param.keySet().iterator();
        while (itre.hasNext()) {
            String paramKey = itre.next();
            StringBody stringBody = new StringBody(
                    (String) param.get(paramKey));
            multipartEntity.addPart(paramKey, stringBody);
        }
        multipartEntity.addPart("file", bABody);
        httpost.setEntity(multipartEntity);
        HttpResponse response = null;
        try {
            response = httpclient.execute(httpost);
        } catch (Exception e) {
            httpclient.getConnectionManager().closeExpiredConnections();
            httpclient.getConnectionManager().closeIdleConnections(0,
                    TimeUnit.SECONDS);
            response = httpclient.execute(httpost);
        }
        HttpEntity entity = response.getEntity();
        String result = null;
        if (entity != null) {
            result = toString(entity.getContent(), HTTP.UTF_8);
            entity.consumeContent();
        }
        return result;
    }
    
    @SuppressWarnings({ "deprecation" })
    public static String doPostJsonRequest(String url,
            Map<String, Object> param) throws IOException {
        HttpPost httpost = new HttpPost(url);
        // List<NameValuePair> nvps = setNameValuePair(param);
        String json = JsonUtils.toJson(param);
        HttpClient httpclient = HttpConnectionManager.getHTTPSClient();
        httpost.setEntity(new StringEntity(json, HTTP.UTF_8));
        httpost.setHeader("Connection", "close");
        httpost.setHeader("sysIdentity", Constant.SYTEM_IDENTITY);

        Map<String, Object> treeMap = new TreeMap<String, Object>(
                new CollatorComparator());
        if (MapUtils.isNotEmpty(param)) {
            treeMap.putAll(param);
        }
        StringBuilder entryptString = new StringBuilder("");
        for (Entry<String, Object> entry : treeMap.entrySet()) {
            entryptString
                    .append(entry.getKey() + "=" + entry.getValue().toString());
        }
        // 拼接系统密钥
        entryptString.append(Constant.ENCRYPT_KEY);
        // MD5加密
        String md5Sign = DigestUtils
                .md5DigestAsHex(entryptString.toString().getBytes("UTF-8"));
        httpost.setHeader("sign", md5Sign);

        HttpResponse response = null;

        try {
            response = httpclient.execute(httpost);
        } catch (Exception e) {
            httpclient.getConnectionManager().closeExpiredConnections();
            httpclient.getConnectionManager().closeIdleConnections(0,
                    TimeUnit.SECONDS);
            response = httpclient.execute(httpost);
        }

        HttpEntity entity = response.getEntity();
        String result = null;
        if (entity != null) {
            result = toString(entity.getContent(), HTTP.UTF_8);
            entity.consumeContent();
        }
        return result;

    }

    @SuppressWarnings({ "deprecation" })
    public static String doPostRequest(String url, Map<String, String> param) {
        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> nvps = setNameValuePair(param);
        try {
            HttpClient httpclient = HttpConnectionManager.getHTTPSClient();
            httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
            httpost.setHeader("Connection", "close");
            httpost.setHeader("sysIdentity", Constant.SYTEM_IDENTITY);
            
            Map<String, String> treeMap = new TreeMap<String, String>(
                    new CollatorComparator());
            if (MapUtils.isNotEmpty(param)) {
                treeMap.putAll(param);
            }
            StringBuilder entryptString = new StringBuilder("");
            for (Entry<String, String> entry : treeMap.entrySet()) {
                entryptString.append(entry.getKey() + "=" + entry.getValue());
            }
            // 拼接系统密钥
            entryptString.append(Constant.ENCRYPT_KEY);
            // MD5加密
            String md5Sign = DigestUtils
                    .md5DigestAsHex(entryptString.toString().getBytes("UTF-8"));
            httpost.setHeader("sign", md5Sign);

            HttpResponse response = null;

            try {
                response = httpclient.execute(httpost);
            } catch (Exception e) {
                httpclient.getConnectionManager().closeExpiredConnections();
                httpclient.getConnectionManager().closeIdleConnections(0,
                        TimeUnit.SECONDS);
                response = httpclient.execute(httpost);
            }

            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = toString(entity.getContent(), HTTP.UTF_8);
                entity.consumeContent();
            }
            return result;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return "";
    }

    @SuppressWarnings({ "deprecation" })
    public static String doUploadServerPostRequest(String url,
            Map<String, String> param) {
        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> nvps = setNameValuePair(param);
        try {
            HttpClient httpclient = HttpConnectionManager.getHTTPSClient();
            httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
            httpost.setHeader("Connection", "close");
            httpost.setHeader("sysIdentity", Constant.SYTEM_IDENTITY);
            httpost.setHeader("CONTENT-TYPE", "application/json");
            Map<String, String> treeMap = new TreeMap<String, String>(
                    new CollatorComparator());
            if (MapUtils.isNotEmpty(param)) {
                treeMap.putAll(param);
            }
            StringBuilder entryptString = new StringBuilder("");
            for (Entry<String, String> entry : treeMap.entrySet()) {
                entryptString.append(entry.getKey() + "=" + entry.getValue());
            }
            // 拼接系统密钥
            entryptString.append(Constant.ENCRYPT_KEY);
            // MD5加密
            String md5Sign = DigestUtils
                    .md5DigestAsHex(entryptString.toString().getBytes("UTF-8"));
            httpost.setHeader("sign", md5Sign);

            HttpResponse response = null;

            try {
                response = httpclient.execute(httpost);
            } catch (Exception e) {
                httpclient.getConnectionManager().closeExpiredConnections();
                httpclient.getConnectionManager().closeIdleConnections(0,
                        TimeUnit.SECONDS);
                response = httpclient.execute(httpost);
            }

            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = toString(entity.getContent(), HTTP.UTF_8);
                entity.consumeContent();
            }
            return result;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return "";
    }
    
    @SuppressWarnings("deprecation")
    public static String readResponse(final HttpEntity httpEntity)
            throws Exception {
        if (httpEntity != null) {
            InputStreamReader inputStreamReader = null;
            inputStreamReader = new InputStreamReader(httpEntity.getContent());
            BufferedReader bufferedReader = new BufferedReader(
                    inputStreamReader);
            StringBuffer sb = new StringBuffer();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(new String(line.getBytes(),
                        Charset.forName(HTTP.UTF_8)));
            }
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            return sb.toString();
        }
        return "";
    }

    @SuppressWarnings("rawtypes")
    private static List<NameValuePair> setNameValuePair(Map param) {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if (param == null) {
            return nvps;
        }

        Iterator entries = param.entrySet().iterator();
        Map.Entry entry;
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            String name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if (null == valueObj) {
                nvps.add(new BasicNameValuePair(name, ""));
            } else if (valueObj instanceof String[]) {
                String[] values = (String[]) valueObj;
                for (int i = 0; i < values.length; i++) {
                    nvps.add(new BasicNameValuePair(name, values[i]));
                }
            } else {
                nvps.add(new BasicNameValuePair(name, valueObj.toString()));
            }
        }

        return nvps;
    }

    private static String toString(InputStream in, String encode) {
        StringBuffer result = new StringBuffer();
        try {
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(in, encode));
            String tempLine = rd.readLine();
            while (tempLine != null) {
                result.append(tempLine);
                tempLine = rd.readLine();
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return result.toString();
    }

    @SuppressWarnings("unchecked")
    public static void sendPost(String url, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String info = doPostRequest(url, getEncryptSignByRequest(request),
                request.getParameterMap());
        renderHtml(response, info);
    }

    private static String getEncryptSignByRequest(HttpServletRequest request) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder("");
        if (StringUtils.equalsIgnoreCase("application/json",
                request.getContentType())) {
            try {
                BufferedReader reader = request.getReader();
                char[] buff = new char[BUFFER_SIZE];
                int len;
                while ((len = reader.read(buff)) != -1) {
                    sb.append(buff, 0, len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // 验证系统正确及参数正确性
            Map<String, String> treeMap = new TreeMap<String, String>(
                    new CollatorComparator());
            // 获取所有的参数名称
            @SuppressWarnings("unchecked")
            Enumeration<String> parameterNames = request.getParameterNames();
            // 将所有的请求参数加入到 TreeMap(有顺序)中
            while (parameterNames.hasMoreElements()) {
                String parameterName = parameterNames.nextElement();
                String[] values = request.getParameterValues(parameterName);
                treeMap.put(parameterName, StringUtils.join(values, ","));
            }

            // 拼接所有的参数
            for (Entry<String, String> entry : treeMap.entrySet()) {
                sb.append(entry.getKey() + "=" + entry.getValue());
            }
        }
        // 拼接系统密钥
        sb.append(Constant.ENCRYPT_KEY);
        // MD5加密
        String md5Sign = DigestUtils
                .md5DigestAsHex(sb.toString().getBytes("UTF-8"));
        return md5Sign;
    }

    @SuppressWarnings("unchecked")
    public static void sendPostJson(String url, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String info = doPostRequest(url, getEncryptSignByRequest(request),
                request.getParameterMap());
        renderJson(response, info);
    }

    @SuppressWarnings({ "deprecation" })
    public static String doPostRequest(String url, String md5Sign,
            Map<String, String> param) {
        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> nvps = setNameValuePair(param);
        try {
            HttpClient httpclient = HttpConnectionManager.getHTTPSClient();
            httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
            httpost.setHeader("Connection", "close");
            httpost.setHeader("sysIdentity", Constant.SYTEM_IDENTITY);

            httpost.setHeader("sign", md5Sign);

            HttpResponse response = null;

            try {
                response = httpclient.execute(httpost);
            } catch (Exception e) {
                httpclient.getConnectionManager().closeExpiredConnections();
                httpclient.getConnectionManager().closeIdleConnections(0,
                        TimeUnit.SECONDS);
                response = httpclient.execute(httpost);
            }

            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = toString(entity.getContent(), HTTP.UTF_8);
                entity.consumeContent();
            }
            return result;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return "";
    }

    public static void sendGetJson(String url, Map<String, String> params,
            HttpServletResponse response) throws Exception {
        String info = doGetRequest(url, params);
        renderJson(response, info);
    }

    @SuppressWarnings({ "deprecation" })
    public static void renderJsonContentType(String url, String json,
            HttpServletResponse response) throws Exception {

        HttpResponse httpResp = null;
        try {
            HttpClient client = HttpConnectionManager.getHTTPSClient();
            HttpPost post = new HttpPost(url);
            post.setHeader("sysIdentity", Constant.SYTEM_IDENTITY);
            StringBuilder sb = new StringBuilder(json);
            // 拼接系统密钥
            sb.append(Constant.ENCRYPT_KEY);
            // MD5加密
            String md5Sign = DigestUtils
                    .md5DigestAsHex(sb.toString().getBytes("UTF-8"));
            post.setHeader("sign", md5Sign);
            StringEntity entity = new StringEntity(json, "utf-8");// 解决中文乱码问题
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            post.setEntity(entity);

            try {
                httpResp = client.execute(post);
            } catch (Exception e) {
                client.getConnectionManager().closeExpiredConnections();
                client.getConnectionManager().closeIdleConnections(0,
                        TimeUnit.SECONDS);
                httpResp = client.execute(post);
            }

            HttpEntity respEntity = httpResp.getEntity();
            String result = null;
            if (respEntity != null) {
                result = toString(respEntity.getContent(), HTTP.UTF_8);
                respEntity.consumeContent();
            }
            renderJson(response, result);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }

    @SuppressWarnings("deprecation")
    public static String sendJsonContentType(String url, String json) {
        HttpResponse httpResp = null;
        try {
            HttpClient client = HttpConnectionManager.getHTTPSClient();
            HttpPost post = new HttpPost(url);
            post.setHeader("sysIdentity", Constant.SYTEM_IDENTITY);
            StringBuilder sb = new StringBuilder(json);
            // 拼接系统密钥

            sb.append(Constant.ENCRYPT_KEY);
            // MD5加密

            String md5Sign = DigestUtils
                    .md5DigestAsHex(sb.toString().getBytes("UTF-8"));
            post.setHeader("sign", md5Sign);

            StringEntity entity = new StringEntity(json, "utf-8");// 解决中文乱码问题
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            post.setEntity(entity);

            try {
                httpResp = client.execute(post);
            } catch (Exception e) {
                client.getConnectionManager().closeExpiredConnections();
                client.getConnectionManager().closeIdleConnections(0,
                        TimeUnit.SECONDS);
                httpResp = client.execute(post);
            }

            HttpEntity respEntity = httpResp.getEntity();
            String result = null;
            if (respEntity != null) {
                result = toString(respEntity.getContent(), HTTP.UTF_8);
                respEntity.consumeContent();
            }
            return result;
        } catch (Exception e1) {
            e1.printStackTrace();
            return "";
        }
    }

    /**
     * 发送文本。使用UTF-8编码。
     * 
     * @param response
     *            HttpServletResponse
     * @param text
     *            发送的字符串
     */
    public static void renderHtml(HttpServletResponse response, String text) {
        render(response, "text/html;charset=UTF-8", text);
    }

    /**
     * 发送文本。使用UTF-8编码。
     * 
     * @param response
     *            HttpServletResponse
     * @param text
     *            发送的字符串
     */
    public static void renderText(HttpServletResponse response, String text) {
        render(response, "text/plain;charset=UTF-8", text);
    }

    /**
     * 发送json。使用UTF-8编码。
     * 
     * @param response
     *            HttpServletResponse
     * @param text
     *            发送的字符串
     */
    public static void renderJson(HttpServletResponse response, String text) {
        render(response, "application/json;charset=UTF-8", text);
    }

    /**
     * 发送xml。使用UTF-8编码。
     * 
     * @param response
     *            HttpServletResponse
     * @param text
     *            发送的字符串
     */
    public static void renderXml(HttpServletResponse response, String text) {
        render(response, "text/xml;charset=UTF-8", text);
    }

    /**
     * 发送内容。使用UTF-8编码。
     * 
     * @param response
     * @param contentType
     * @param text
     */
    public static void render(HttpServletResponse response, String contentType,
            String text) {
        response.setContentType(contentType);
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        if (text != null) {
            try {
                response.getWriter().write(text);
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    public static InputStream getUrlInputStream(String strUrl)
            throws IOException {
        InputStream is = null;
        URL url = null;
        url = new URL(strUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();// 利用HttpURLConnection对象,我们可以从网络中获取网页数据.
        conn.setDoInput(true);
        conn.connect();
        is = conn.getInputStream(); // 得到网络返回的输入流
        return is;
    }

    /**
     * 该方法仅用于系统间接口调用认证时向UASC发送验证请求
     * 
     * @param url
     * @param sourceSystemId
     *            源调用者系统ID
     * @param sourceSign
     *            源调用者系统签名
     * @param request
     * @return
     */
    @SuppressWarnings("all")
    public static String doPostRequest(String url, String sourceSystemId,
            String sourceSign, HttpServletRequest request) {
        HttpPost httpost = new HttpPost(url);

        try {
            HttpClient httpclient = HttpConnectionManager.getHTTPSClient();

            httpost.setHeader("Connection", "close");
            httpost.setHeader("sysIdentity", Constant.SYTEM_IDENTITY);
            httpost.setHeader("sourceSystemId", sourceSystemId);
            httpost.setHeader("sourceSign", sourceSign);

            String md5Sign = getEncryptSignByRequest(request);
            httpost.setHeader("sign", md5Sign);

            if (StringUtils.equalsIgnoreCase("application/json",
                    request.getContentType())) {
                StringBuffer sbb = new StringBuffer();
                try {
                    BufferedReader reader = request.getReader();
                    char[] buff = new char[BUFFER_SIZE];
                    int len;
                    while ((len = reader.read(buff)) != -1) {
                        sbb.append(buff, 0, len);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                StringEntity entity = new StringEntity(new String(
                        sbb.toString().getBytes("UTF8"), "ISO8859-1"));// 解决中文乱码问题
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                httpost.setEntity(entity);
            } else {
                List<NameValuePair> nvps = setNameValuePair(
                        request.getParameterMap());
                httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
            }

            HttpResponse response = null;

            try {
                response = httpclient.execute(httpost);
            } catch (Exception e) {
                httpclient.getConnectionManager().closeExpiredConnections();
                httpclient.getConnectionManager().closeIdleConnections(0,
                        TimeUnit.SECONDS);
                response = httpclient.execute(httpost);
            }

            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = toString(entity.getContent(), HTTP.UTF_8);
                entity.consumeContent();
            }
            return result;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return "";
    }

    public static void sendPostJson(String url, String json,
            HttpServletResponse response) {
        renderJson(response, sendJsonContentType(url, json));
    }

}
