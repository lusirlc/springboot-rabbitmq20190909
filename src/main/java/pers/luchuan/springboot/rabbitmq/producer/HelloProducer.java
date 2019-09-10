package pers.luchuan.springboot.rabbitmq.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.luchuan.springboot.rabbitmq.constant.RmConst;

import java.util.Random;
import java.util.UUID;

/**
 * Created By Lu Chuan On 2019/9/9
 */
@Component
public class HelloProducer {
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	public void send(String msg) {
		System.out.println("HelloProducer:send message "+msg);
		CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
		Random random = new Random();
		int r = random.nextInt(10);
		if (r > 5) {
			rabbitTemplate.convertAndSend(RmConst.EXCHANGE_DIRECT, RmConst.RK_HELLO, msg,correlationData);
		} else {
			rabbitTemplate.convertAndSend(RmConst.EXCHANGE_DIRECT, "hello", msg,correlationData);
		}
	}
}
