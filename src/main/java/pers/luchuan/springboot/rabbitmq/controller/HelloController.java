package pers.luchuan.springboot.rabbitmq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.luchuan.springboot.rabbitmq.producer.HelloProducer;

/**
 * Created By Lu Chuan On 2019/9/9
 */
@RestController
@RequestMapping("hello")
public class HelloController {
	
	@Autowired
	private HelloProducer helloProducer;
	
	@GetMapping("{msg}")
	public void sendMsg(@PathVariable String msg) {
		for (int i = 0; i < 50; i++) {
			helloProducer.send(msg+i);
		}
	}
}
