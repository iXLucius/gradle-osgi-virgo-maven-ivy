package org.lucius.framework.utils.common;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class Networks {
	public static String getHostName() {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			return "localhost";
		}
	}

	public static String getHostAddress() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			return "127.0.0.1";
		}
	}

	public static final void main(String[] args) throws SocketException {
		System.out.println(getHostAddress());
		Enumeration<NetworkInterface> interfaces = NetworkInterface
				.getNetworkInterfaces();
		while (interfaces.hasMoreElements()) {
			NetworkInterface current = interfaces.nextElement();
			if (!current.isUp() || current.isLoopback() || current.isVirtual())
				continue;
			Enumeration<InetAddress> addresses = current.getInetAddresses();
			while (addresses.hasMoreElements()) {
				InetAddress current_addr = addresses.nextElement();
				if (current_addr.isLoopbackAddress())
					continue;
				System.out.println(current_addr.getHostAddress());
			}
		}
	}
}
