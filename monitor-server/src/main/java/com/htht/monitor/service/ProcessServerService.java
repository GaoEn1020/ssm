package com.htht.monitor.service;

import java.util.List;

import com.htht.monitor.bean.ProcessServer;

/**
 * 服务层接口类
 *
 */
public interface ProcessServerService {
	
	//查询数据库列表
	public List<ProcessServer> getAllList();
	
	//通过id查询IP
	public String getIPByProcessID(String processId);
}
