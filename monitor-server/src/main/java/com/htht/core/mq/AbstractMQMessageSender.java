package com.htht.core.mq;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import net.sf.json.JSONObject;
import org.apache.tools.ant.util.DateUtils;
import com.htht.util.AppLogService;
import com.htht.util.DIEIProperties;
import com.htht.util.Log;
import com.htht.util.MQConfigUtil;
import com.htht.util.RestfulUtils;

public abstract class AbstractMQMessageSender {

	protected  transient ConnectionFactory factory;
	protected transient Connection connection;
	protected transient Session session;
	protected transient MessageProducer producer;
	
	public AbstractMQMessageSender() throws Exception{
		factory = ConnectionFactoryManager.getInstance();
		connection = factory.createConnection();
		try {
			connection.start();
		} catch (JMSException jmse) {
			sendEI(MQConfigUtil.getBrokerURL(), jmse.getMessage());
			connection.close();
			throw jmse;
		}
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	}
	
	private void sendEI(String brokerURL, String errorMsg){
		String type = "SYSTEM.ALARM.EI";
		String name = "EI告警信息";// 定时产品任务运行详细信息
		String message = "EI告警信息";// 定时产品任务运行详细信息
		String system = "DPC";
//		Map<String, Object> tags = new HashMap<String, Object>();
//		tags.put("SYSTEM", system);// 加工系统?DPC?
		Map<String, Object> fields = new HashMap<String, Object>();
		fields.put("SYSTEM", system);
		fields.put("GROUP_ID", "OP_DPC_Z");
		fields.put("ORG_TIME", "");// YYYYMMDDThhmmss
		fields.put("MSG_TYPE", "03");
		fields.put("COL_TYPE", "01");
		fields.put("DATA_FROM", "BABJ");
		fields.put("EVENT_TYPE", "OP_DPC_Z_1-05-03");//OP_MICAPS_A-1-10-02
		fields.put("EVENT_LEVEL", "2");
		fields.put("EVENT_TITLE", "DPC-任务中间件错误");
		fields.put("KObject", "调度与计算节点通信异常");
		fields.put("KEvent", errorMsg);
		fields.put("KResult", "任务执行环境配置失败");
		fields.put("KIndex", "中间件配置["+brokerURL+"]");
		fields.put("KComment", "mq");
		fields.put("EVENT_TIME", DateUtils.format(new Date(), "YYYY-MM-dd HH:mm:ss"));
		fields.put("EVENT_SUGGEST", "0,0,0,1$检查通信中间件配置；检查通信中间件运行状态");
		fields.put("EVENT_CONTROL", "0");
		fields.put("EVENT_TRAG", "1通信中间件配置不正确2通信中间件服务端运行不正确");
		fields.put("EVENT_EXT1", "");
		String hostAddress = "";
		InetAddress address = null;
		try {
			address = InetAddress.getLocalHost();
			hostAddress =address.getHostAddress();
		} catch (UnknownHostException e) {
//			e.printStackTrace();
			AppLogService.error("get ipaddr exception {"+e.getMessage()+"}", e);
		}//获取的是本地的IP地址 //PC-20140317PXKX/192.168.0.121
		fields.put("EVENT_EXT2", hostAddress);
		Log eiLog = RestfulUtils.createEI(type, name, message, fields);
		String url = DIEIProperties.getInstance().getSingleUrl();
		AppLogService.info(" restful return {"+eiLog.toString()+"}");
		String res = RestfulUtils.Post(url, JSONObject.fromObject(eiLog)
				.toString());
		if (res != null){
			AppLogService.info(" restful return {"+res+"}");
		}else{
			AppLogService.info(" restful failed ");
		}
	}
	
	/**
	 * 关闭连接
	 * @throws JMSException
	 */
	public void close() throws JMSException {
		if (connection != null) {
			connection.close();
		}
	}
	
	/**
	 * 消息发送
	 * @param data 消息
	 * @param sendFlag 消息标识
	 * @param className 接收消息的类名
	 * @param method 处理消息的方法名
	 * @return 返回处理结果
	 * @throws JMSException
	 */
	public abstract String sendMessage(String data,String sendFlag,String className,String method) throws JMSException; 
}
