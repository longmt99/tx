package com.tx;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.tx.controller.TXThread;

@SpringBootApplication
public class BootApplication {
	
	@PostConstruct
	public void init() throws Exception {
		System.setProperty("http.agent", "Chrome");
		Thread thread = new Thread(new TXThread());
		thread.start();
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(BootApplication.class, args);
		
		
	}

}