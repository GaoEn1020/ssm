package com.htht.monitor.dao;

import java.util.List;

import com.htht.monitor.bean.ProcessServer;

/**
 * 查询表t_process_server的接口
 *
 */
public interface ProcessServerDao {
	
	//查询所有进程列表
	public List<ProcessServer> getProcessServerList();
	
	//根据id查询IP
	public String getIpById(String id);
}
