package com.htht.core.mq;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.htht.util.MQConfigUtil;

public class ConnectionFactoryManager {

	protected static ConnectionFactory factory;

	private ConnectionFactoryManager() {
	}

	public static ConnectionFactory getInstance() throws Exception {
		try {
			if (factory == null) {
				synchronized (ConnectionFactoryManager.class) {
					if (factory == null) {
						String brokerURL = MQConfigUtil.getBrokerURL();
						if (brokerURL == null) {
							throw new Exception(
									"src目录下没有找到activeMQ.properties文件！");
						}
						factory = new ActiveMQConnectionFactory(brokerURL);
					}
				}
			}

		} catch (Exception e) {
			throw new Exception(e.toString());
		}

		return factory;
	}
}
