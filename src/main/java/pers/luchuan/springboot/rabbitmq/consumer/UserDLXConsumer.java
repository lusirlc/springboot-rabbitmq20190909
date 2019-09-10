package pers.luchuan.springboot.rabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import pers.luchuan.springboot.rabbitmq.constant.RmConst;

/**
 * Created By Lu Chuan On 2019/9/10
 */
@Component
@RabbitListener(queues = RmConst.QUEUE_USER_DLX)
public class UserDLXConsumer {
	@RabbitHandler
	public void process(String msg) {
		System.out.println("UserDLXConsumer:received message "+msg);
	}
}
