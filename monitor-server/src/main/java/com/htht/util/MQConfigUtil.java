package com.htht.util;

import java.util.ResourceBundle;

public class MQConfigUtil {

	protected static ResourceBundle bundle = ResourceBundle.getBundle("activeMQ");
//	protected static ResourceBundle bundle = ResourceBundle.getBundle("activeMQ", Locale.getDefault());
	
	/**
	 * 根据key获取value
	 * @return
	 */
	public static String getValueByKey(String key) {
		if(bundle == null)
		{
			System.err.println("没有对应的资源");
			return null;
		}
		return bundle.getString(key);
	}
	
	/**
	 * 读取activeMQ的服务地址
	 * @return
	 */
	public static String getBrokerURL() {
		if(bundle == null)
		{
			System.err.println("没有对应的资源");
			return null;
		}
		return bundle.getString("brokerURL");
	}
	
	/**
	 * 读取本地IP
	 * @return
	 */
	public static String getLocalIp() {
		if(bundle == null)
		{
			System.err.println("没有对应的资源");
			return null;
		}
		return bundle.getString("localIp");
	}
	
	/**
	 * 读取远程服务器IP
	 * @return
	 */
	public static String getRemoteIp() {
		if(bundle == null)
		{
			System.err.println("没有对应的资源");
			return null;
		}
		return bundle.getString("remoteIp");
	}
}
