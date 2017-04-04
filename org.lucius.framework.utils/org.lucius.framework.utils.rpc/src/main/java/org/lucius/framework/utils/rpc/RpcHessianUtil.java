package org.lucius.framework.utils.rpc;

import java.net.MalformedURLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RpcHessianUtil {
    
	private static Logger logger = LoggerFactory.getLogger(RpcHessianUtil.class);
	
	public static Object getService(@SuppressWarnings("rawtypes") Class api,
			String key,ClassLoader classLoader) throws MalformedURLException {
		IssHessianProxyFactory factory = new IssHessianProxyFactory(classLoader);
		factory.setOverloadEnabled(true);
		factory.setReadTimeout(180000);
		factory.setConnectTimeOut(10000);
		String url = key;
		logger.info("RpcHessianUtil.getService[ " + api + "," + key + "," + url + "]");
		ClassLoader old = Thread.currentThread().getContextClassLoader();
		Thread.currentThread().setContextClassLoader(classLoader);
		Object obj =  factory.create(api,url , classLoader);
		Thread.currentThread().setContextClassLoader(old);
		return obj;
	}
}
