package com.htht.monitor.service;

import java.rmi.RemoteException;

import com.htht.monitor.bean.ResourceInfo;



public interface SysInfo {
	
	/**
	 *  CPU的用户使用量、系统使用剩余量、总的剩余量、总的使用占用量等（单位：100%）
	 */
	public String testCpuPerc();
	
	/**
	 * 内存资源信息
	 */
	public String getPhysicalMemory();
	
	/**
	 * 资源信息（主要是硬盘）
	 * 取硬盘已有的分区及其详细信息（通过sigar.getFileSystemList()来获得FileSystem列表对象，然后对其进行编历）：
	 * @throws Exception
	 */
	public String testFileSystemInfo() throws Exception;

	public String getMemory() throws Exception;
	/**
	 * CPU信息
	 * @return
	 * @throws Exception
	 */
	public String getCpuInfo() throws Exception;
	/**
	 * 系统信息
	 * @return
	 * @throws Exception
	 */
	public String getSystemInfo() throws Exception;
	
	/**
	 * 系统信息
	 * @return
	 * @throws Exception
	 */
	public ResourceInfo getResourceInfo() throws RemoteException;
	
	/**
	 * 内存信息
	 * @return
	 * @throws Exception
	 */
	public String getMemoryInfo() throws Exception;
	/**
	 * 网络信息
	 * @return
	 * @throws Exception
	 */
	public String getNetInfo() throws Exception;
	
	/**
	 * CPU 使用率
	 * @return
	 * @throws Exception
	 */
	public double getCpuRatio() throws Exception;
}
