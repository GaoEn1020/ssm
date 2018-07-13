package com.htht.core.mq;

import java.util.Date;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import com.htht.util.MQConfigUtil;

/**
 * 同步发送消息，需要等待消息反馈然后再处理
 * @author htht
 *
 */
public class MQMessageSendSync extends AbstractMQMessageSender implements MessageListener {
	
	protected Boolean isCallBack = false;
	
	protected String returnData;
	
	/**
	 * 等待时间，单位是秒。默认是1800秒
	 */
	protected int delayTime=1800;
	
	public MQMessageSendSync() throws Exception {
		super();
	}
	
	/**
	 * 
	 * @param delayTime 等待时间，单位是秒
	 * @throws Exception
	 */
	public MQMessageSendSync(int delayTime) throws Exception {
		super();
		this.delayTime = delayTime;
	}
	
	/**
	 * 发送消息
	 * @param data 消息
	 * @param sendFlag 消息标识
	 * @param className 接收的类名称
	 * @param method 具体的执行方法名
	 * @throws JMSException
	 */
	@Override
	public String sendMessage(String data,String sendFlag,String className,String method) throws JMSException {
		Destination destination = session.createQueue(sendFlag);
		producer = session.createProducer(destination);
		producer.setDeliveryMode(DeliveryMode.PERSISTENT);
		MapMessage message = session.createMapMessage();
		message.setString("data", data);
		message.setString("className", className);
		message.setString("method", method);
		//producer.send(message);
		
		//回执信息设置
		Destination tempDest = session.createTemporaryQueue();
		MessageConsumer responseConsumer = session.createConsumer(tempDest);
		responseConsumer.setMessageListener(this);
		message.setJMSReplyTo(tempDest);
		message.setJMSCorrelationID(new Date().getTime()+"");
		producer.send(message);
		int count=0;
		while(!isCallBack && count <delayTime){
			try {
				Thread.sleep(1000);
				count++;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		close();
		return returnData;
	}

	@Override
	public void onMessage(Message msg) {
        try {  
            if (msg instanceof TextMessage) {  
                TextMessage textMessage = (TextMessage) msg;  
                returnData=textMessage.getText();
            } 
            else if (msg instanceof MapMessage) {
            	MapMessage mapMessage = (MapMessage)msg;
				returnData =mapMessage.getString("data");
			}
            synchronized (isCallBack) {
         	   isCallBack = true;
			} 
        } catch (JMSException e) {  
        }
	}
	
	public static void main(String[] args) throws Exception {
		String downloadIp = MQConfigUtil.getValueByKey("localIP");
		for(int i=0;i<10;i++)
		{
			MQMessageSendSync sender = new MQMessageSendSync();
			String data="FY3C_FTP";
			System.out.println("发送信息："+data);
			String returnInfo =sender.sendMessage(data,downloadIp,"",MethodConstant.getResourceInfo);
			System.out.println("接收信息："+returnInfo);
		}
	}
}
