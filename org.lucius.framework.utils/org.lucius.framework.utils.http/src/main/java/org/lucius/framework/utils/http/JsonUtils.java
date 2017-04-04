package org.lucius.framework.utils.http;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.util.Assert;

public final class JsonUtils {
    private static ObjectMapper objectMapper = new ObjectMapper();
    
    /**
     * 对象转JSON
     * @param value 对象
     * @return
     */
    public static String toJson(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return null;
    }
    
    /**
     * 
     * @param response response
     * @param contentType contentType
     * @param value 对象
     */
    public static void toJson(HttpServletResponse response, String contentType,Object value) {
        Assert.notNull(response);
        Assert.hasText(contentType);
        try {
            response.setContentType(contentType);
            objectMapper.writeValue(response.getWriter(), value);
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }
    
    /**
     * 对象转JSON
     * @param response response
     * @param value value
     */
    public static void toJson(HttpServletResponse response, Object value) {
        Assert.notNull(response);
        PrintWriter localPrintWriter = null;
        try {
            response.setContentType("text/plain; charset=utf-8");
            localPrintWriter = response.getWriter();
            objectMapper.writeValue(localPrintWriter, value);
            localPrintWriter.flush();
        } catch (Exception localException) {
            localException.printStackTrace();
        } finally {
            IOUtils.closeQuietly(localPrintWriter);
        }
    }
    
    /**
     * JSON转对象
     * @param json json
     * @param valueType 类型
     * @param <T> 类型
     * @return
     */
    public static <T> T toObject(String json, Class<T> valueType) {
        Assert.hasText(json);
        Assert.notNull(valueType);
        try {
            return objectMapper.readValue(json, valueType);
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return null;
    }
    
    /**
     * JSON转对象
     * @param json json
     * @param typeReference 类型
     * @param <T> 类型
     * @return
     */
    public static <T> T toObject(String json, TypeReference<?> typeReference) {
        Assert.hasText(json);
        Assert.notNull(typeReference);
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return null;
    }
    
    /**
     * JSON转对象
     * @param json json json
     * @param javaType 类型
     * @param <T> 类型
     * @return
     */
    public static <T> T toObject(String json, JavaType javaType) {
        Assert.hasText(json);
        Assert.notNull(javaType);
        try {
            return objectMapper.readValue(json, javaType);
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return null;
    }
}
