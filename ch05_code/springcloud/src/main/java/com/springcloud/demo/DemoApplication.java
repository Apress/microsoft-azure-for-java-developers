package com.springcloud.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootApplication
@RestController
public class DemoApplication {

	@Autowired
	private StringRedisTemplate template;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@RequestMapping("/hello")
    public String hello() {
		ValueOperations<String, String> ops = this.template.opsForValue();

      	// Add a Hello World string to your cache.
      	String key = "helloworld";
      	if (!this.template.hasKey(key)) {
          ops.set(key, "Hello World !!!!");
      	}
		return ops.get(key);
    }

}
