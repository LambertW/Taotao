package com.lambertwu.activemq;

import java.io.Console;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;

import com.alibaba.druid.sql.dialect.oracle.ast.clause.ModelClause.CellAssignment;

public class TestActiveMq {

	@Test
	public void testQueueProducer() throws Exception {
		// 创建连接工厂对象ConnectionFactory
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		// 使用工厂创建连接
		Connection connection = connectionFactory.createConnection();
		// 开启连接
		connection.start();
		// 使用Connection创建一个Session
		// 参数1：是否开启事务，一般不使用，保证最终一致性
		// 参数2：消息的应答模式
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 使用Session对象创建一个Destination对象（形式Queue，Topic）
		Queue queue = session.createQueue("test-queue");
		// 使用Session对象创建一个Producer对象
		MessageProducer producer = session.createProducer(queue);
		// 创建一个TextMessage对象
		TextMessage textMessage = new ActiveMQTextMessage();
		textMessage.setText("hello activemq");
		// 发送消息
		producer.send(textMessage);
		// 关闭资源
		producer.close();
		session.close();
		connection.close();
	}
	
	@Test
	public void testQueueConsumer() throws Exception {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Queue queue = session.createQueue("test-queue");
		MessageConsumer consumer = session.createConsumer(queue);
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				if(message instanceof TextMessage) {
					TextMessage textMessage = (TextMessage)message;
					try {
						String text = textMessage.getText();
						System.out.println(text);
					} catch (JMSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
		System.in.read();
		
		consumer.close();
		session.close();
		connection.close();
	}
	
	@Test
	public void testTopicProducer() throws Exception {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("test-topic");
		MessageProducer producer = session.createProducer(topic);
		TextMessage message = session.createTextMessage("hello activemq topic");
		producer.send(message);
		
		producer.close();
		session.close();
		connection.close();
	}
	
	@Test
	public void testTopicConsumer() throws Exception {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("test-topic");
		MessageConsumer consumer = session.createConsumer(topic);
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				if(message instanceof TextMessage) {
					TextMessage textMessage = (TextMessage)message;
					try {
						String text = textMessage.getText();
						System.out.println(text);
					} catch (JMSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
		System.out.println("Topic消费者1");
		
		System.in.read();
		
		consumer.close();
		session.close();
		connection.close();
	}
	
}
