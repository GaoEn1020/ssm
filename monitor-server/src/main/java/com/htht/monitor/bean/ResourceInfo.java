package com.htht.monitor.bean;

import java.io.Serializable;

public class ResourceInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//主机名
	private String hostName="";
	//系统
	private String os = "";
	//系统版本
	private String osVersion = "";
	//ip地址
	private String ipAdress="";
	//操作系统版本
	private String system="";
	//机器状态
	private String status="";
	//内存总量
	private String totalMem="";
	//内存使用量
	private String usedMem="";
	//内存空余量
	private String freeMem="";
	//交换区总量
	private String totalSwap="";
	//交换区使用量
	private String usedSwap="";
	//交换区剩余量
	private String freeSwap="";
//	//盘符数量
//	private int diskNum;
//	//硬盘信息
//	private List<diskInfo>  diskInformation;
	//CPU数量
	private String cpuNum="";
	//CPU使用率信息
//	private List<cpuInfo> cpuRate;
	private String cpuRate="";
	
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getIpAdress() {
		return ipAdress;
	}
	public void setIpAdress(String ipAdress) {
		this.ipAdress = ipAdress;
	}

	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	public void setTotalMem(String totalMem) {
		this.totalMem = totalMem;
	}
	public String getTotalMem() {
		return totalMem;
	}
	public void setUsedMem(String usedMem) {
		this.usedMem = usedMem;
	}
	public String getUsedMem() {
		return usedMem;
	}
	public void setFreeMem(String freeMem) {
		this.freeMem = freeMem;
	}
	public String getFreeMem() {
		return freeMem;
	}
	public void setTotalSwap(String totalSwap) {
		this.totalSwap = totalSwap;
	}
	public String getTotalSwap() {
		return totalSwap;
	}
	public void setUsedSwap(String usedSwap) {
		this.usedSwap = usedSwap;
	}
	public String getUsedSwap() {
		return usedSwap;
	}
	public void setFreeSwap(String freeSwap) {
		this.freeSwap = freeSwap;
	}
	public String getFreeSwap() {
		return freeSwap;
	}
//	public void setDiskNum(int diskNum) {
//		this.diskNum = diskNum;
//	}
//	public int getDiskNum() {
//		return diskNum;
//	}
//	public void setDiskInformation(List<diskInfo> diskInformation) {
//		this.diskInformation = diskInformation;
//	}
//	public List<diskInfo> getDiskInformation() {
//		return diskInformation;
//	}
	public void setCpuNum(String cpuNum) {
		this.cpuNum = cpuNum;
	}
	public String getCpuNum() {
		return cpuNum;
	}
	public void setCpuRate(String cpuRate) {
		this.cpuRate = cpuRate;
	}
	public String getCpuRate() {
		return cpuRate;
	}
	public String getOsVersion() {
		return osVersion;
	}
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	@Override
	public String toString() {
		return "ResourceInfo [hostName=" + hostName + ", os=" + os
				+ ", osVersion=" + osVersion
				+  ", ipAdress=" + ipAdress + ", system=" + system + ", status="
				+ status + ", totalMem=" + totalMem + ", usedMem=" + usedMem
				+ ", freeMem=" + freeMem + ", totalSwap=" + totalSwap
				+ ", usedSwap=" + usedSwap + ", freeSwap=" + freeSwap
				+ ", cpuNum=" + cpuNum + ", cpuRate=" + cpuRate + "]";
	}
	
	
}
