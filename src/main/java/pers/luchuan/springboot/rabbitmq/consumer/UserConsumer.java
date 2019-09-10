package pers.luchuan.springboot.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;
import pers.luchuan.springboot.rabbitmq.constant.RmConst;

import java.util.Random;

/**
 * Created By Lu Chuan On 2019/9/9
 */
@Component
//@RabbitListener(queues = RmConst.QUEUE_USER)
public class UserConsumer implements ChannelAwareMessageListener{
//	@RabbitHandler
//	public void process(String msg) {
//		System.out.println(UserConsumer.class.getName()+":received message "+msg);
//	}
	
	@Override
	public void onMessage(Message message, Channel channel) throws Exception {//消费者手动确认
		String msg = new String(message.getBody());
		try {
			Thread.sleep(1000);
			Random random = new Random();
			int r = random.nextInt(10);
			if (r > 7) {
				throw new RuntimeException("user server error");
			}
			System.out.println("UserConsumer:received message "+msg);
			channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
		} catch (Exception e) {
			e.printStackTrace();
			// 不要求重发，但是把失败的消息发送到死信队列
		    channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);
		    // 要求消息重发
//		    channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
		}
	}
}
