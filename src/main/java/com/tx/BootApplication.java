package com.tx;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.tx.model.TXThread;

@SpringBootApplication
public class BootApplication {
	
	@PostConstruct
	public void init() throws Exception {
		Thread t1 = new Thread(new TXThread());
		//t1.start();
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(BootApplication.class, args);
		
		
	}

}