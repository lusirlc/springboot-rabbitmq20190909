package pers.luchuan.springboot.rabbitmq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.luchuan.springboot.rabbitmq.producer.HelloProducer;
import pers.luchuan.springboot.rabbitmq.producer.UserProducer;

/**
 * Created By Lu Chuan On 2019/9/9
 */
@RestController
@RequestMapping("user")
public class UserController {
	
	@Autowired
	private UserProducer userProducer;
	
	@GetMapping("{msg}")
	public void sendMsg(@PathVariable String msg) {
		for (int i = 0; i < 50; i++) {
			userProducer.send(msg+i);
		}
	}
}
