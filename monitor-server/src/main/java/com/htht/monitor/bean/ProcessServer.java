package com.htht.monitor.bean;

import com.htht.util.QueueStatistics;

/**
 * 类名：ProcessServer
 * 对应数据库名：t_process_server
 *
 */
public class ProcessServer {
	
	private String id;
	
	private String hostname;
	
	private String ip;
	
	private String notes;
	
	private double status;
	
	private String createtime;
	
	private String updatetime;
	
	private String createby;
	
	private String updateby;
	
	private double processingcapacity;
	
	private String classify;
	
	private String port;
	//系统类型：1-linux;2-windows;3-storm-nimbus;4-spark-master;5-docker;
	private int os_type;
	//集群节点数
	private double cluster_nodes;
	//应对的集群服务器ID
	private int colony_id;
	//服务器类型 1：控制节点 2：计算节点
	private int server_type;
	
	private QueueStatistics queueStatistics;
	
	public QueueStatistics getQueueStatistics() {
		return queueStatistics;
	}
	public void setQueueStatistics(QueueStatistics queueStatistics) {
		this.queueStatistics = queueStatistics;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
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
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public String getCreateby() {
		return createby;
	}
	public void setCreateby(String createby) {
		this.createby = createby;
	}
	public String getUpdateby() {
		return updateby;
	}
	public void setUpdateby(String updateby) {
		this.updateby = updateby;
	}
	public double getProcessingcapacity() {
		return processingcapacity;
	}
	public void setProcessingcapacity(double processingcapacity) {
		this.processingcapacity = processingcapacity;
	}
	public String getClassify() {
		return classify;
	}
	public void setClassify(String classify) {
		this.classify = classify;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public int getOs_type() {
		return os_type;
	}
	public void setOs_type(int os_type) {
		this.os_type = os_type;
	}
	public double getCluster_nodes() {
		return cluster_nodes;
	}
	public void setCluster_nodes(double cluster_nodes) {
		this.cluster_nodes = cluster_nodes;
	}
	public int getColony_id() {
		return colony_id;
	}
	public void setColony_id(int colony_id) {
		this.colony_id = colony_id;
	}
	public int getServer_type() {
		return server_type;
	}
	public void setServer_type(int server_type) {
		this.server_type = server_type;
	}
	
	
	
}
