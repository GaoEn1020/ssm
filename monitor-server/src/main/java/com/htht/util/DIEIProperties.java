package com.htht.util;

import java.io.InputStream;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;


/**
 * 关于 RabbitMQ 的配置。
 * 
 * @author zhaoxia
 *
 */
public class DIEIProperties {
	private static final DIEIProperties instance = new DIEIProperties();
	private static final Logger logger = Logger.getLogger(DIEIProperties.class);
	
	private String singleUrl;
	private String batchUrl;
	
	private DIEIProperties(){
		PropertiesConfiguration properties = new PropertiesConfiguration();
		try{
			InputStream inputStream =  getClass().getResourceAsStream(CONSTANT.FILE_PATH);
			// file path 对应的文件不存在时，inputStream=null。
			if (inputStream == null) {
				return;
			}
			properties.load(inputStream, CONSTANT.UTF_8);
		} catch (ConfigurationException e) {
			logger.error("Create instanceof DiEiProperties : " , e);
		} 
		load(properties);
	}
	
	public static DIEIProperties getInstance(){
		return instance;
	}
	
	private static final class CONSTANT {
		private final static String UTF_8 = "utf-8";
		private final static String FILE_PATH = "/diei.properties";

		private final static String DIEI = "diei";
		private final static String DOT = ".";
		private final static String SINGLE = "single";
		private final static String BATCH = "batch";
		private final static String URL = "url";

		private final static String DIEI_SINGLE_URL = DIEI + DOT
				+ SINGLE + DOT + URL;
		private final static String DIEI_BATCH_URL = DIEI + DOT
				+ BATCH + DOT + URL;
	}
	
	protected void load(PropertiesConfiguration properties) {
		this.singleUrl = properties.getString(CONSTANT.DIEI_SINGLE_URL, null);
		this.batchUrl = properties.getString(CONSTANT.DIEI_BATCH_URL, null);
	}

	public String getSingleUrl() {
		return singleUrl;
	}

	public void setSingleUrl(String singleUrl) {
		this.singleUrl = singleUrl;
	}

	public String getBatchUrl() {
		return batchUrl;
	}

	public void setBatchUrl(String batchUrl) {
		this.batchUrl = batchUrl;
	}

	public static void main(String[] args) {
		String singleUrl = DIEIProperties.getInstance().getSingleUrl();
		String batchUrl = DIEIProperties.getInstance().getBatchUrl();
		System.out.println(singleUrl);
		System.out.println(batchUrl);
	}

}
