package org.lucius.framework.web.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HttpServletRequest 包装类， 解决 request.getReader()不能重复使用的问题 
 */
public class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {
    
    
    private static final Logger logger = LoggerFactory.getLogger(BodyReaderHttpServletRequestWrapper.class);

    private final byte[] body;

    public BodyReaderHttpServletRequestWrapper(HttpServletRequest request)
            throws IOException {
        super(request);
        if(StringUtils.equalsIgnoreCase("application/json", request.getContentType())){
            StringBuilder sb = new StringBuilder("");
            try {
                BufferedReader reader = request.getReader();
                char[] buff = new char[1024];
                int len;
                while((len = reader.read(buff)) != -1) {
                    sb.append(buff,0, len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            body = sb.toString().getBytes("UTF-8");
            logger.info("obtain reqeust body is {} ",sb.toString());
        }else{
            body = null;
        }
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return bais.read();
            }
        };
    }

}
