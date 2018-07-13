package com.htht.monitor.service.impl;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.text.DecimalFormat;

import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.NetFlags;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.OperatingSystem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.SigarNotImplementedException;
import org.hyperic.sigar.Swap;
import org.springframework.stereotype.Service;

import com.htht.monitor.bean.ResourceInfo;
import com.htht.monitor.service.SysInfo;
import com.htht.util.CPUMonitor;

@Service
public class SysInfoImpl implements SysInfo {
	private DecimalFormat df = new DecimalFormat("#0.00");
	private DecimalFormat df1 = new DecimalFormat("#0");

	/**
	 * 内存资源信息
	 */
	@Override
	public String getPhysicalMemory() {

		StringBuilder dataXML = new StringBuilder();

		// a)物理内存信息
		Sigar sigar = new Sigar();
		Mem mem;
		try {
			mem = sigar.getMem();
			// // 内存总量
			// String Total= "内存总量:"+ df.format((float) mem.getTotal() / 1024 /
			// 1024 / 1024)+ "G";
			Float totalF = Float
					.parseFloat(df.format((float) mem.getTotal() / 1024 / 1024 / 1024));

			// 当前内存使用量
			String cur = "当前内存使用量:"
					+ df.format((float) mem.getUsed() / 1024 / 1024 / 1024)
					+ "G";
			Float curf = Float
					.parseFloat(df.format((float) mem.getUsed() / 1024 / 1024 / 1024));

			// 当前内存剩余量
			String remain = "当前内存剩余量:"
					+ df.format((float) mem.getFree() / 1024 / 1024 / 1024)
					+ "G";
			Float remainF = Float
					.parseFloat(df.format((float) mem.getFree() / 1024 / 1024 / 1024));

			// b)系统页面文件交换区信息
			// Swap swap = sigar.getSwap();
			// // 交换区总量
			// String swapTotal="交换区总量:"+ df.format((float) swap.getTotal() /
			// 1024 / 1024 / 1024)+ "G";
			// // 当前交换区使用量
			// String swapCur="当前交换区使用量:"+ df.format((float) swap.getUsed() /
			// 1024 / 1024 / 1024)+ "G";
			// // 当前交换区剩余量
			// String swapRemail="当前交换区剩余量"+ df.format((float) swap.getFree() /
			// 1024 / 1024 / 1024)+ "G";
			//

			dataXML.append("[");
			dataXML.append("['" + cur + "=>占全部'," + (curf / totalF) * 100
					+ "],");
			dataXML.append("['" + remain + "=>占全部'," + (remainF / totalF) * 100
					+ "]]");

		} catch (SigarException e) {
			e.printStackTrace();
		}
		System.out.println(dataXML.toString());
		return dataXML.toString();
	}

	/**
	 * 内存资源信息
	 */
	@Override
	public String getMemory() {

		// a)物理内存信息
		Sigar sigar = new Sigar();
		Mem mem;
		try {
			mem = sigar.getMem();
			// 内存总量
			Float totalF = Float
					.parseFloat(df.format((float) mem.getTotal() / 1024 / 1024 / 1024));
			// 当前内存使用量
			Float curf = Float
					.parseFloat(df.format((float) mem.getUsed() / 1024 / 1024 / 1024));
			return df.format((curf / totalF) * 100).toString();
		} catch (SigarException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getMemoryInfo() {
		// a)物理内存信息
		Sigar sigar = new Sigar();
		Mem mem;
		try {
			mem = sigar.getMem();
			// 内存总量
			String Total = df
					.format((float) mem.getTotal() / 1024 / 1024 / 1024) + "GB";

			return Total;
		} catch (SigarException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * CPU的用户使用量、系统使用剩余量、总的剩余量、总的使用占用量等（单位：100%）
	 */
	// @Override
	// public String testCpuPerc() {
	//
	// StringBuilder dataXML= new StringBuilder();
	// dataXML.append("[");
	//
	// Sigar sigar = new Sigar();
	// // 方式一，主要是针对一块CPU的情况
	// // CpuPerc cpu;
	// // try {
	// // cpu = sigar.getCpuPerc();
	// // printCpuPerc(cpu);
	// // } catch (SigarException e) {
	// // e.printStackTrace();
	// // }
	//
	// // 方式二，不管是单块CPU还是多CPU都适用
	// CpuPerc cpuList[] = null;
	// try {
	// cpuList = sigar.getCpuPercList();
	// } catch (SigarException e) {
	// e.printStackTrace();
	// return null;
	// }
	// for (int i = 0; i < cpuList.length; i++) {
	// String val = CpuPerc.format(cpuList[i].getCombined());
	// String idelVal = CpuPerc.format(cpuList[i].getIdle());
	//
	// int intVal = (int)(cpuList[i].getCombined()*100);
	// int idelValue = 100-intVal;
	//
	// dataXML.append("['"+i+"号cpu当前使用率"+val+"=>占全部',  "+intVal+"],");
	// dataXML.append(" ['"+i+"号cpu当前空闲率"+idelVal+"=>占全部' , "+idelValue+"]");
	// if(i<cpuList.length-1){
	// dataXML.append(",");
	// }
	// }
	//
	// dataXML.append("]");
	//
	// return dataXML.toString();
	// }

	/**
	 * CPU的用户使用量、系统使用剩余量、总的剩余量、总的使用占用量等（单位：100%）
	 */
	@Override
	public String testCpuPerc() {
		Sigar sigar = new Sigar();
		CpuPerc cpuList[] = null;
		try {
			cpuList = sigar.getCpuPercList();
		} catch (SigarException e) {
			e.printStackTrace();
			return null;
		}
		double intVal = 0.0;
		for (int i = 0; i < cpuList.length; i++) {
			// String val = CpuPerc.format(cpuList[i].getCombined());
			// String idelVal=CpuPerc.format(cpuList[i].getIdle());
			// double idelValue=100-intVal;
			intVal += cpuList[i].getCombined() * 100;
		}
		intVal = intVal / cpuList.length;
		return intVal + "";
	}

	/**
	 * 资源信息（主要是硬盘）
	 * 取硬盘已有的分区及其详细信息（通过sigar.getFileSystemList()来获得FileSystem列表对象，然后对其进行编历）：
	 * 
	 * @throws Exception
	 */
	@Override
	public String testFileSystemInfo() throws Exception {
	StringBuffer dataXML = new StringBuffer(" [");//返回窜
	StringBuffer diskBuf = new StringBuffer("["); //磁盘
	StringBuffer usedBuf = new StringBuffer("[");    //已用空间
	StringBuffer idelBuf = new StringBuffer("[");    //可用空间
	
	Sigar sigar = new Sigar();
	FileSystem fslist[] = sigar.getFileSystemList();

	boolean flag = true;
	for (int i = 0; i < fslist.length; i++) {

		FileSystem fs = fslist[i];
		// 分区的盘符名称
		String diskName = fs.getDirName();
		 

		FileSystemUsage usage = null;
		try {
			usage = sigar.getFileSystemUsage(fs.getDirName());
		} catch (SigarException e) {
			continue;
		}
		if (fs.getType() == 2) {
			if(!flag){
				diskBuf.append(",");
				usedBuf.append(",");
				idelBuf.append(",");
			}
			flag = false;
			diskBuf.append("'").append(diskName.replace(File.separator, "")).append("盘").append("'");
			usedBuf.append(df1.format((float) usage.getUsed() / 1024 / 1024));
			idelBuf.append(df1.format((float) usage.getFree() / 1024 / 1024));
		}
	}

	dataXML.append(diskBuf.toString()).append("],").append(usedBuf.toString()).append("],").append(idelBuf.toString()).append("]]");
	return dataXML.toString();
	}

	public int getCpuCount() throws SigarException {
		Sigar sigar = new Sigar();
		try {
			return sigar.getCpuInfoList().length;
		} finally {
			sigar.close();
		}
	}

	@Override
	public String getCpuInfo() throws SigarException {
		Sigar sigar = new Sigar();
		try {
			CpuInfo cInfo = sigar.getCpuInfoList()[0];
			DecimalFormat df1 = new DecimalFormat("###.00");
			StringBuffer cpuInfo = new StringBuffer();
			cpuInfo.append(cInfo.getVendor()).append(" ");
			cpuInfo.append(cInfo.getModel()).append("   ");
			cpuInfo.append(df1.format(cInfo.getMhz() / 1000.0)).append("GHz");
			return cpuInfo.toString();
		} finally {
			sigar.close();
		}
	}

	//@Override
	public String getNetInfo() throws SigarException {
		Sigar sigar = new Sigar();
		try {

			return null;
		} finally {
			sigar.close();
		}
	}

	@Override
	public String getSystemInfo() {
		StringBuffer dataXML = new StringBuffer("{");//返回窜
		OperatingSystem OS = OperatingSystem.getInstance();
		// 操作系统内核类型如： 386、486、586等x86
//		System.out.println("OS.getArch() = " + OS.getArch());
//		System.out.println("OS.getCpuEndian() = " + OS.getCpuEndian());//
//		System.out.println("OS.getDataModel() = " + OS.getDataModel());//
//		// 系统描述
//		System.out.println("OS.getDescription() = " + OS.getDescription());
//		System.out.println("OS.getMachine() = " + OS.getMachine());//
//		// 操作系统类型
//		System.out.println("OS.getName() = " + OS.getName());
//		System.out.println("OS.getPatchLevel() = " + OS.getPatchLevel());//
//		// 操作系统的卖主
//		System.out.println("OS.getVendor() = " + OS.getVendor());
//		// 卖主名称
//		System.out
//				.println("OS.getVendorCodeName() = " + OS.getVendorCodeName());
//		// 操作系统名称
//		System.out.println("OS.getVendorName() = " + OS.getVendorName());
//		// 操作系统卖主类型
//		System.out.println("OS.getVendorVersion() = " + OS.getVendorVersion());
//		// 操作系统的版本号
//		System.out.println("OS.getVersion() = " + OS.getVersion());

		dataXML.append("os:'").append(OS.getVendorName()).append("',");
		dataXML.append("version:'").append(OS.getVersion()).append("',");
		dataXML.append("arch:'").append(OS.getArch()).append("',");
		dataXML.append("description:'").append(OS.getDescription()).append("'}");
		
		return dataXML.toString();
	}

	@Override
	public ResourceInfo getResourceInfo() throws RemoteException {
		// TODO Auto-generated method stub
    	Sigar sigar = new Sigar();
    	ResourceInfo resInfo = new ResourceInfo();
    	
    	try {
			resInfo.setHostName(sigar.getNetInfo().getHostName());
			resInfo.setIpAdress(sigar.getNetInterfaceConfig().getAddress());
			resInfo.setSystem(OperatingSystem.getInstance().getName());
			resInfo.setOs(OperatingSystem.getInstance().getVendorName());
			resInfo.setOsVersion(OperatingSystem.getInstance().getVendorVersion());
			 
			resInfo.setStatus("正常");
			
			DecimalFormat df = new DecimalFormat("#0.00");
			Mem mem = sigar.getMem();
			// 内存总量
			String Total= df.format((float) mem.getTotal() / 1024 / 1024 / 1024)+ "G";
			// 当前内存使用量
			String cur= df.format((float) mem.getUsed() / 1024 / 1024 / 1024)+ "G";
			// 当前内存剩余量
			String remain = df.format((float) mem.getFree() / 1024 / 1024 / 1024)+ "G";
			resInfo.setTotalMem(Total);
			resInfo.setUsedMem(cur);
			resInfo.setFreeMem(remain);
			
			Swap swap = sigar.getSwap();
			// 交换区总量
			String swapTotal= df.format((float) swap.getTotal() / 1024 / 1024 / 1024)+ "G";
			// 当前交换区使用量
			String swapCur= df.format((float) swap.getUsed() / 1024 / 1024 / 1024)+ "G";
			// 当前交换区剩余量
			String swapRemail= df.format((float) swap.getFree() / 1024 / 1024 / 1024)+ "G";
			
			resInfo.setTotalSwap(swapTotal);
			resInfo.setUsedSwap(swapCur);
			resInfo.setFreeSwap(swapRemail);
			CpuPerc cpuList[] = null;
			cpuList = sigar.getCpuPercList();
			resInfo.setCpuNum(Integer.toString(cpuList.length));
			
			double sum_time = 0;
			for(int i =0;i<cpuList.length;i++)
			{
				sum_time += cpuList[i].getCombined();
			}
			int aver_time = (int)(sum_time*100/cpuList.length);
			resInfo.setCpuRate(aver_time + "%");
 
		} catch (SigarException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	
		return resInfo;
	}
	
	/**
	 * 当前机器的正式域名
	 * 
	 * @return
	 */
	public String getFQDN() {
		Sigar sigar = null;
		try {
			return InetAddress.getLocalHost().getCanonicalHostName();
		} catch (UnknownHostException e) {
			try {
				sigar = new Sigar();
				return sigar.getFQDN();
			} catch (SigarException ex) {
				return null;
			} finally {
				sigar.close();
			}
		}
	}

	/**
	 * 取到当前机器的IP地址
	 * 
	 * @return
	 */
	public String getDefaultIpAddress() {
		String address = null;
		try {
			address = InetAddress.getLocalHost().getHostAddress();
			// 没有出现异常而正常当取到的IP时，如果取到的不是网卡循回地址时就返回
			// //
			// 否则再通过Sigar工具包中的方法来获取
			if (!NetFlags.LOOPBACK_ADDRESS.equals(address)) {
				return address;
			}
		} catch (UnknownHostException e) {
			// hostname not in DNS or /etc/hosts
		}
		Sigar sigar = new Sigar();
		try {
			try {
				address = sigar.getNetInterfaceConfig().getAddress();
			} catch (SigarException e) {
				e.printStackTrace();
			}
		} finally {
			sigar.close();
		}
		return address;
	}

	/**
	 * 取到当前机器的MAC地址
	 * 
	 * @return
	 */
	public String getMAC() {
		Sigar sigar = null;
		try {
			sigar = new Sigar();
			String[] ifaces = sigar.getNetInterfaceList();
			String hwaddr = null;
			for (int i = 0; i < ifaces.length; i++) {
				NetInterfaceConfig cfg = sigar.getNetInterfaceConfig(ifaces[i]);
				if (NetFlags.LOOPBACK_ADDRESS.equals(cfg.getAddress())
						|| (cfg.getFlags() & NetFlags.IFF_LOOPBACK) != 0
						|| NetFlags.NULL_HWADDR.equals(cfg.getHwaddr())) {
					continue;
				} /*
				 * * 如果存在多张网卡包括虚拟机的网卡，默认只取第一张网卡的MAC地址，如果要返回所有的网卡（包括物理的和虚拟的）
				 * 则可以修改方法的返回类型为数组或Collection * ，通过在for循环里取到的多个MAC地址。
				 */
				hwaddr = cfg.getHwaddr();
				break;
			}
			return hwaddr != null ? hwaddr : null;
		} catch (Exception e) {
			return null;
		} finally {
			if (sigar != null)
				sigar.close();
		}
	}

	/**
	 * 获取网络流量等信息
	 * 
	 * @throws Exception
	 */
	public void testNetIfList() throws Exception {
		Sigar sigar = new Sigar();
		String ifNames[] = sigar.getNetInterfaceList();
		for (int i = 0; i < ifNames.length; i++) {
			String name = ifNames[i];
			NetInterfaceConfig ifconfig = sigar.getNetInterfaceConfig(name);
			 
			
			
			if ((ifconfig.getFlags() & 1L) <= 0L) {
				System.out.println("!IFF_UP...skipping getNetInterfaceStat");
				continue;
			}
			
			System.out.println("\nname = " + name); // 网络设备名
			System.out.println("Address = " + ifconfig.getAddress()); // IP地址
			System.out.println("Netmask = " + ifconfig.getNetmask()); // 子网掩码
			try {
				NetInterfaceStat ifstat = sigar.getNetInterfaceStat(name);
				System.out.println("RxPackets = " + ifstat.getRxPackets());// 接收的总包裹数
				System.out.println("TxPackets = " + ifstat.getTxPackets()); // 发送的总包裹数
				System.out.println("RxBytes = " + ifstat.getRxBytes()); // 接收到的总字节数
				System.out.println("TxBytes = " + ifstat.getTxBytes()); // 发送的总字节数
				System.out.println("RxErrors = " + ifstat.getRxErrors());// 接收到的错误包数
				System.out.println("TxErrors = " + ifstat.getTxErrors());// 发送数据包时的错误数
				System.out.println("RxDropped = " + ifstat.getRxDropped());// 接收时丢弃的包数
				System.out.println("TxDropped = " + ifstat.getTxDropped());// 发送时丢弃的包数
			} catch (SigarNotImplementedException e) {

			} catch (SigarException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	 
	//@Override
	public double getCpuRatio() throws Exception {
		  return CPUMonitor.cpuut;
	}

	public static void main(String[] args) {
		 try {
			 SysInfoImpl sysInfoImpl = new SysInfoImpl(); 
			System.out.print(sysInfoImpl.testFileSystemInfo().toString());
			System.out.println(sysInfoImpl.getSystemInfo().toString());
//			 [['C:盘','D:盘','E:盘'],[20,22,90],[130,368,301]]{os:'Windows 7',version:'6.1',
//				 arch:'x64',description:'Microsoft Windows 7'}
			System.out.println(sysInfoImpl.getResourceInfo().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
