package pers.luchuan.springboot.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.luchuan.springboot.rabbitmq.constant.RmConst;
import pers.luchuan.springboot.rabbitmq.consumer.UserConsumer;

import java.util.HashMap;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

/**
 * Created By Lu Chuan On 2019/9/9
 */
@Configuration
public class RabbitUserConfig {
	
	@Bean("userQueue")
	public Queue queue() {
		HashMap<String, Object> args = new HashMap<>();
		args.put(RmConst.EXCHANGE_DLX_FIELD, RmConst.EXCHANGE_DLX);
		args.put(RmConst.RK_DLX_FIELD, RmConst.RK_USER_DLX);
		return QueueBuilder.durable(RmConst.QUEUE_USER).withArguments(args).build();
	}
	
	@Bean("userExchange")
	public DirectExchange exchange() {
		return (DirectExchange) ExchangeBuilder.directExchange(RmConst.EXCHANGE_DIRECT).durable(true).build();
	}
	
	@Bean("userBinding")
	public Binding binding() {
		return BindingBuilder.bind(queue()).to(exchange()).with(RmConst.RK_USER);
	}
	
	
}
