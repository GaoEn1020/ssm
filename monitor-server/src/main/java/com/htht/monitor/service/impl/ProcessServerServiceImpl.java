package com.htht.monitor.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.htht.monitor.bean.ProcessServer;
import com.htht.monitor.dao.ProcessServerDao;
import com.htht.monitor.service.ProcessServerService;

@Service
public class ProcessServerServiceImpl implements ProcessServerService {

	@Autowired
	private ProcessServerDao processServerDao;
	
	@Override
	public List<ProcessServer> getAllList() {
		List<ProcessServer> list = processServerDao.getProcessServerList();
		
//		for(ProcessServer p : list){
//			
//		}
		return list;
	}

	@Override
	public String getIPByProcessID(String processId) {
		String ip = processServerDao.getIpById(processId);
		//未查询到相应的IP
		if(StringUtils.isBlank(ip)){
			return "";
		}
		return ip;
	}

}
