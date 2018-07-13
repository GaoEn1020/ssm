package com.htht.monitor.bean;

/**
 * 类名：MasWebServer
 * 对应的数据库名称：t_mas_webserver
 *
 */
public class WebServer {
	
	private String id;
	
	private String name;
	
	private String notes;
	
	private String wsdlurl;
	
	private String interfacename;
	
	private double status;
	
	private String createtime;
	
	private String createby;
	
	private String updatetime;
	
	private String updateby;
	
	private String processserverid;
	
	private int counter;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getWsdlurl() {
		return wsdlurl;
	}

	public void setWsdlurl(String wsdlurl) {
		this.wsdlurl = wsdlurl;
	}

	public String getInterfacename() {
		return interfacename;
	}

	public void setInterfacename(String interfacename) {
		this.interfacename = interfacename;
	}

	public double getStatus() {
		return status;
	}

	public void setStatus(double status) {
		this.status = status;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getCreateby() {
		return createby;
	}

	public void setCreateby(String createby) {
		this.createby = createby;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public String getUpdateby() {
		return updateby;
	}

	public void setUpdateby(String updateby) {
		this.updateby = updateby;
	}

	public String getProcessserverid() {
		return processserverid;
	}

	public void setProcessserverid(String processserverid) {
		this.processserverid = processserverid;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}
	
	
	
}
