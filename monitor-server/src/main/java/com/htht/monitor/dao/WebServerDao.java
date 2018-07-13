package com.htht.monitor.dao;

import java.util.List;

import com.htht.monitor.bean.WebServer;

/**
 * 查询接口：WebServerDao
 * 查询数据表：t_mas_webserver
 *
 */
public interface WebServerDao {
	
	//查询list
	public List<WebServer> getWebServerList();
}
