package pers.luchuan.springboot.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.luchuan.springboot.rabbitmq.constant.RmConst;

/**
 * Created By Lu Chuan On 2019/9/10
 */
@Configuration
public class RabbitUserDLXConfig {
	@Bean("userDlxQueue")
	public Queue queue() {
		return QueueBuilder.durable(RmConst.QUEUE_USER_DLX).build();
	}
	
	@Bean("userDlxExchange")
	public DirectExchange exchange() {
		return (DirectExchange) ExchangeBuilder.directExchange(RmConst.EXCHANGE_DLX).durable(true).build();
	}
	
	@Bean("userDlxBinding")
	public Binding binding() {
		return BindingBuilder.bind(queue()).to(exchange()).with(RmConst.RK_USER_DLX);
	}
}
