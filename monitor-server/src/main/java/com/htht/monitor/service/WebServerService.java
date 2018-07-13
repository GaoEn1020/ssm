package com.htht.monitor.service;

import java.util.List;

import com.htht.monitor.bean.WebServer;

/**
 * WebServer接口
 *
 */
public interface WebServerService {
	
	//查询webServerlist
	public List<WebServer> getWebServerList();
}
