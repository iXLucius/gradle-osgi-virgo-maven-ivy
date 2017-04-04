package org.lucius.framework.utils.rpc;

import java.lang.reflect.Method;
import java.net.URL;

import com.caucho.hessian.client.HessianProxy;
import com.caucho.hessian.client.HessianProxyFactory;

public class IssHessianProxy extends HessianProxy {

	protected IssHessianProxy(URL url, HessianProxyFactory factory) {
		super(url, factory);
	}

	/**
	 * Handles the object invocation.
	 * 
	 * @param proxy
	 *            the proxy object to invoke
	 * @param method
	 *            the method to call
	 * @param args
	 *            the arguments to the proxy object
	 */
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		ClassLoader old = Thread.currentThread().getContextClassLoader();
		Thread.currentThread().setContextClassLoader(((IssHessianProxyFactory)this._factory).getClassLoader());
		Object obj = super.invoke(proxy, method, args);
		Thread.currentThread().setContextClassLoader(old);
		return obj;
	}

}
