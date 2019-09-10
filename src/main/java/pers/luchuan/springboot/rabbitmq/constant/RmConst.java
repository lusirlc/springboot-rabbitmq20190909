package pers.luchuan.springboot.rabbitmq.constant;

/**
 * RabbitMQ交换器、路由、队列配置
 * Created By Lu Chuan On 2019/9/9
 */
public class RmConst {
	//=========交换器=============
	public final static String EXCHANGE_DIRECT = "sb_direct_exchange";
	public final static String EXCHANGE_TOPIC = "sb_topic_exchange";
	public final static String EXCHANGE_FANOUT = "sb_fanout_exchange";
	public final static String EXCHANGE_DLX = "sb_dlx_exchange";
	
	//=========队列=============
	public final static String QUEUE_USER = "sb_queue_user";
	public final static String QUEUE_HELLO = "sb_queue_hello";
	public final static String QUEUE_USER_DLX = "sb_dlx_queue_user";
	
	//=========路由=============
	public final static String RK_USER = "sb.user";
	public final static String RK_HELLO = "sb.hello";
	public final static String RK_USER_DLX = "sb.user.dlx";
	
	//=========声明死信交换机和路由属性=============
	public final static String EXCHANGE_DLX_FIELD = "x-dead-letter-exchange";
	public final static String RK_DLX_FIELD = "x-dead-letter-routing-key";
}
