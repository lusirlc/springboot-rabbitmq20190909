package pers.luchuan.springboot.rabbitmq.config;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import pers.luchuan.springboot.rabbitmq.constant.RmConst;
import pers.luchuan.springboot.rabbitmq.consumer.UserConsumer;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;

/**
 * Created By Lu Chuan On 2019/9/9
 */
@Configuration
public class RabbitConfig {
	@Value("${spring.rabbitmq.host}")
	private String host;
	@Value("${spring.rabbitmq.port}")
	private Integer port;
	@Value("${spring.rabbitmq.username}")
	private String username;
	@Value("${spring.rabbitmq.password}")
	private String password;
	@Value("${spring.rabbitmq.virtual-host}")
	private String virtualHost;
	@Value("${spring.rabbitmq.publisher-confirms}")
	private boolean publisherConfirms;
	@Value("${spring.rabbitmq.cache.channel.size}")
	private Integer cacheChannelSize;
	@Autowired
	private UserConsumer userConsumer;
	
	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory factory = new CachingConnectionFactory(host,port);
		factory.setUsername(username);
		factory.setPassword(password);
		factory.setChannelCacheSize(cacheChannelSize);
		factory.setPublisherConfirms(publisherConfirms);
		factory.setVirtualHost(virtualHost);
		return factory;
	}
	
	@Bean
	public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
		return new RabbitAdmin(connectionFactory);
	}
	
	// 发布者确认
	@Bean
	public RabbitTemplate.ConfirmCallback confirmCallback() {
		return new RabbitTemplate.ConfirmCallback() {
			@Override
			public void confirm(CorrelationData correlationData, boolean ack, String cause) {
				if (ack) {
					System.out.println("send to the mq successfully");
				} else {
					System.out.println("send to the mq failed because "+cause+"and the correlationId is "+correlationData.getId());
				}
			}
		};
	}
	
	// 路由失败通知
	@Bean
	public RabbitTemplate.ReturnCallback returnCallback() {
		return new RabbitTemplate.ReturnCallback() {
			@Override
			public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				String msg = null;
				try {
					msg = new String(message.getBody(), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				map.put("correlationId", message.getMessageProperties().getCorrelationIdString());
				map.put("message", msg);
				map.put("replyCode", replyCode);
				map.put("replyText", replyText);
				map.put("exchange", exchange);
				map.put("routingKey", routingKey);
				String replyInfo = JSON.toJSONString(map);
				System.out.println("send to the queue failed:"+replyInfo);
			}
		};
	}
	
	//消息监听容器（消费者手动确认需要）
	@Bean("userMessageListener")
	public SimpleMessageListenerContainer userMessageListenerContainer() {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory());
		container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
		container.setQueueNames(RmConst.QUEUE_USER);
		container.setMessageListener(userConsumer);
		container.setConcurrentConsumers(2);//设置消费者数量
		container.setPrefetchCount(1);
		return container;
	}
	
	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
		template.setMandatory(true);
		template.setConfirmCallback(confirmCallback());
		template.setReturnCallback(returnCallback());
		return template;
	}
	
}
