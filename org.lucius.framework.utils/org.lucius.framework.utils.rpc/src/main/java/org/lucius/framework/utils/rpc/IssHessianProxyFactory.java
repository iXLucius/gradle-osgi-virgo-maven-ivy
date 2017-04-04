package org.lucius.framework.utils.rpc;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.caucho.hessian.client.HessianProxyFactory;
import com.caucho.hessian.io.HessianRemoteObject;

public class IssHessianProxyFactory extends HessianProxyFactory {

	private ClassLoader classLoader;
	private long connectTimeOut = -1;

	public IssHessianProxyFactory(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	public Object create(@SuppressWarnings("rawtypes") Class api,
			String urlName, ClassLoader loader) throws MalformedURLException {
		if (api == null)
			throw new NullPointerException(
					"api must not be null for HessianProxyFactory.create()");
		InvocationHandler handler = null;

		URL url = new URL(urlName);
	
		handler = new IssHessianProxy(url, this);
		return Proxy.newProxyInstance(loader, new Class[] { api,
				HessianRemoteObject.class }, handler);
	}

	public ClassLoader getClassLoader() {
		return classLoader;
	}

	public void setClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	public URLConnection openConnection(URL url) throws IOException {
		URLConnection conn = super.openConnection(url);
		if (connectTimeOut > 0) {
			try {
				conn.setConnectTimeout((int) connectTimeOut);
			} catch (Throwable e) {
			}
		}
		return conn;
	}

	public long getConnectTimeOut() {
		return connectTimeOut;
	}

	public void setConnectTimeOut(long connectTimeOut) {
		this.connectTimeOut = connectTimeOut;
	}
}
