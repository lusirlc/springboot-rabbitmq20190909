package pers.luchuan.springboot.rabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import pers.luchuan.springboot.rabbitmq.constant.RmConst;

/**
 * Created By Lu Chuan On 2019/9/9
 */
@RabbitListener(queues = RmConst.QUEUE_HELLO) //消费者自动确认
public class HelloConsumer {
	private String name;
	
	public HelloConsumer(String name) {
		this.name = name;
	}
	
	@RabbitHandler
	public void process(String msg) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(name+":received message "+msg);
	}
}
