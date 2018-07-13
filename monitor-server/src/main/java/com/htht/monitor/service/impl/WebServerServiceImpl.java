package com.htht.monitor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.htht.monitor.bean.WebServer;
import com.htht.monitor.dao.WebServerDao;
import com.htht.monitor.service.WebServerService;

@Service
public class WebServerServiceImpl implements WebServerService {
	
	@Autowired
	private WebServerDao webServerDao;
	
	@Override
	public List<WebServer> getWebServerList() {
		return webServerDao.getWebServerList();
	}

}
