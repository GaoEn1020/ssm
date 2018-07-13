package com.htht.monitor.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.htht.core.HostServerQueue;
import com.htht.core.mq.AbstractMQMessageSender;
import com.htht.core.mq.ConnectionFactoryManager;
import com.htht.core.mq.MQMessageSendSync;
import com.htht.core.mq.MethodConstant;
import com.htht.monitor.bean.ProcessServer;
import com.htht.monitor.bean.ResourceInfo;
import com.htht.monitor.bean.WebServer;
import com.htht.monitor.service.ProcessServerService;
import com.htht.monitor.service.WebServerService;
import com.htht.util.QueueStatistics;

@Controller
@RequestMapping("/processServer")
public class ProcessServerController {
	
	public static Map<String,String> serviceConfiguration = null;
	
	@Autowired
	private ProcessServerService processServerService;
	
	@Autowired
	private WebServerService webServerService;
	
	//测试一下
	@RequestMapping("/hello")
	@ResponseBody
	public String getHelloWorld(){
		return "Hello world!!";
	}
	//测试跳转页面
	@RequestMapping("/test")
	public String getModel(Model model){
		model.addAttribute("msg","跳转页面测试");
//        return "test";
		return "/monitor/systemMonitor_info";
	}
	
	@RequestMapping("/test01")
	public String getModel01(Model model){
		model.addAttribute("msg","跳转页面测试");
//        return "test";
		return "/monitor/systemMonitor";
	}
	
	//测试数据库
	@RequestMapping("/getAll")
	@ResponseBody
	public Object getProcessServerList(){
		return processServerService.getAllList();
	}
	
	/**
	 * 获取集群监控信息
	 * @param map
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getProcessServerlist")
	@ResponseBody
	public List<ProcessServer> getProcessServerlist() {
		
		//WSDL　地址
		//Map m = this.serverHostLogic.list(-1, -1, map);
		
		List<ProcessServer> processServerlist = processServerService.getAllList();
		if(processServerlist == null || processServerlist.isEmpty()){
			return null;
		}
		
		QueueStatistics queueStatistics = null;
		for(ProcessServer processServer : processServerlist){
			//应对的集群服务器ID
			int colonyId = processServer.getColony_id();
			//系统类型：1-linux;2-windows;3-storm-nimbus;4-spark-master;5-docker;
			int osType = processServer.getOs_type();
			//int deployType = 0;
//			if(colonyId == 0){
//				deployType = 1;
//			}else{
//				ClusterServerInfo csi = serverHostLogic.getClusterServerDAO().get(colonyId);
//				deployType = csi.getColonyType();
//			}
			String ip = processServer.getIp();
			//queueStatistics = getQueueStatisticsInfo(colonyId, osType, ip);
			processServer.setQueueStatistics(queueStatistics);
		}
		
		return processServerlist;
	}
	
	/**
	 * 获取服务器信息
	 * @param webServers
	 * @return
	 */
	@RequestMapping("/getResourceInfo")
	@ResponseBody
	public ResourceInfo getResourceInfo(){
		
		ResourceInfo ri = null;
//		List<WebServer> webServers = webServerService.getWebServerList();
//		if(webServers == null || webServers.isEmpty()){
//			return ri;
//		}
		
		serviceConfiguration = new HashMap<String,String>();
		serviceConfiguration.put("monitor", "mas2-ws-monitor");
		serviceConfiguration.put("taskService", "HTHT-task-frame");

		String  serviceName = serviceConfiguration.get("monitor");
		/*String  serviceName = HostServerQueue.serviceConfiguration.get("plugin");*/
		
		//for (WebServer webServer : webServers) {
			//if(serviceName.equals(webServer.getWsdlurl())){ 
				//TODO:关联表 Processserverid 即 process表中的id
				//String ip = processServerService.getIPByProcessID(webServer.getProcessserverid());
				//String sysInfoService = "127.0.0.1" +":"+webServer.getWsdlurl();
				String sysInfoService = "127.0.0.1" +":"+serviceName;
				AbstractMQMessageSender sender = null;
				try {
					sender = new MQMessageSendSync(5);
					String result = sender.sendMessage("我试试试试试试", sysInfoService, "", MethodConstant.getResourceInfo);
					if(result!=null && result.length() >0){
						JSONObject object = JSONObject.fromObject(result);
						ri=(ResourceInfo)JSONObject.toBean(object, ResourceInfo.class);
					}
					
				} catch (Exception e1) {
					e1.printStackTrace();
					return ri;
				}
//			}
//		}
		return ri;
	}
	
	/**
	 * 获取服务器信息
	 * @param webServers
	 * @return
	 */
	@RequestMapping("/showResourceInfo")
	public String showResourceInfo(Model model){
		
		ResourceInfo ri = null;
//		List<WebServer> webServers = webServerService.getWebServerList();
//		if(webServers == null || webServers.isEmpty()){
//			return null;
//		}
		
		serviceConfiguration = new HashMap<String,String>();
		serviceConfiguration.put("monitor", "mas2-ws-monitor");
		serviceConfiguration.put("taskService", "HTHT-task-frame");

		String  serviceName = serviceConfiguration.get("taskService");
		/*String  serviceName = HostServerQueue.serviceConfiguration.get("plugin");*/
		
//		for (WebServer webServer : webServers) {
//			if(serviceName.equals(webServer.getWsdlurl())){ 
				//TODO:关联表 Processserverid 即 process表中的id
				//String ip = processServerService.getIPByProcessID(webServer.getProcessserverid());
				//String sysInfoService = "127.0.0.1" +":"+webServer.getWsdlurl();
				String sysInfoService = "127.0.0.1" +":"+serviceName;
				AbstractMQMessageSender sender = null;
				try {
					sender = new MQMessageSendSync(5);
					String result = sender.sendMessage("我测试一下", sysInfoService, "", MethodConstant.getResourceInfo);
					if(result!=null && result.length() >0){
						JSONObject object = JSONObject.fromObject(result);
						ri=(ResourceInfo)JSONObject.toBean(object, ResourceInfo.class);
					}
					
				} catch (Exception e1) {
					e1.printStackTrace();
					return null;
				}
			//}
		//}
		model.addAttribute("resourceInfo",ri);
		return "/monitor/systemMonitor_info";
	}
	
	/**
	 * 检查节点服务器的monitor服务是否正常启动
	 * @param webServers
	 * @return
	 */
//	public int checkServerStatus(Set<WebServer>  webServers) {
//		if(webServers == null || webServers.isEmpty()){
//			return 0;
//		}
//		serviceConfiguration = new HashMap<String,String>();
//		serviceConfiguration.put("monitor", "mas2-ws-monitor");
//		
//		String  serviceName = serviceConfiguration.get("monitor");
//		for (WebServer webServer : webServers) {
//			if(serviceName.equals(webServer.getWsdlurl())){ 
//				//TODO:关联表 Processserverid 即 process表中的id
//				String ip = processServerService.getIPByProcessID(webServer.getProcessserverid());
//				String sysInfoService = ip +":"+webServer.getWsdlurl();
//				try {
//					Long count = ConnectionFactoryManager.getConsumerCountByName(sysInfoService);
//					return count.intValue();
//				} catch (Exception e1) {
//					e1.printStackTrace();
//					return 0;
//				}
//			}
//		}
//		return 0;
//	}
	
	/**
	 * 添加storage对象
	 * @param instance
	 * @return 是否成功
	 */
//	public boolean add(ProcessServer instance) {
//	
////		instance.setUpdateTime(new Timestamp(System.currentTimeMillis()));
//		
//		if(StringUtils.isNotEmpty(instance.getId())){
//			instance.setUpdateBy(LoginUser.getCurrentLoginUser().getUserId());
//			instance.setUpdateTime(new Timestamp(System.currentTimeMillis()));
//			boolean s = this.serverHostLogic.updateInstance(instance, null);
//			//检查队列
//			HostServerQueue.getInstance().checkQueueByService();
//			return s;
//		}else{
//			instance.setCreateBy(LoginUser.getCurrentLoginUser().getUserId());
//			instance.setUpdateBy(null);
//			instance.setCreateTime(new Timestamp(System.currentTimeMillis()));
//			String s = this.serverHostLogic.addInstance(instance);
//			if(s!=null){
//				WebServer webServer1 = new WebServer();
//				WebServer webServer2 = new WebServer();
//				webServer1.setStatus(1);
//				webServer2.setStatus(1);
//				webServer1.setCreateTime(new Timestamp(System.currentTimeMillis()));
//				webServer1.setUpdateTime(new Timestamp(System.currentTimeMillis()));
//				webServer2.setCreateTime(new Timestamp(System.currentTimeMillis()));
//				webServer2.setUpdateTime(new Timestamp(System.currentTimeMillis()));
//				webServer1.setProcessServer(instance);
//				webServer1.setWsdlUrl("mas2-ws-scheduler");
//				webServerLogic.addInstance(webServer1);
//				webServer2.setProcessServer(instance);
//				webServer2.setWsdlUrl("mas2-ws-monitor");
//				webServerLogic.addInstance(webServer2);
//				
//				//检查队列
//				HostServerQueue.getInstance().checkQueueByService();
//				return true;
//			}
//			
//		}
//		
//		//检查队列
//		HostServerQueue.getInstance().checkQueueByService();
//		return false;
//	}
	
}


