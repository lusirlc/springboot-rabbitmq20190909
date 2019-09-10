package pers.luchuan.springboot.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.luchuan.springboot.rabbitmq.constant.RmConst;
import pers.luchuan.springboot.rabbitmq.consumer.HelloConsumer;


/**
 * Created By Lu Chuan On 2019/9/9
 */
@Configuration
public class RabbitHelloConfig {
	
	@Bean
	public Queue helloQueue() {
		return new Queue(RmConst.QUEUE_HELLO, true);
	}
	@Bean
	public DirectExchange helloExchange() {
		return new DirectExchange(RmConst.EXCHANGE_DIRECT, true, false);
	}
	
	@Bean
	public Binding helloBinding() {
		return BindingBuilder.bind(helloQueue()).to(helloExchange()).with(RmConst.RK_HELLO);
	}
	
	@Bean
	public HelloConsumer helloConsumer1() {
		return new HelloConsumer("HelloConsumer1");
	}
	@Bean
	public HelloConsumer helloConsumer2() {
		return new HelloConsumer("HelloConsumer2");
	}
}
