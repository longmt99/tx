package com.tx.controller;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TxController {

	static Logger log = LoggerFactory.getLogger(TxController.class.getName());
	@Value("${size}")
	private int size = 12;
	@Value("${rate}")
	private int rate = 80;
	@Value("${sample}")
	private int sample = 11;

	@PostConstruct
	public void lastResult() {
		
	}
	
	@RequestMapping("/")
	public String config(Map<String, Object> model) {
		model.put("size", this.size);
		model.put("rate", this.rate);
		model.put("sample", this.sample);
		return "live";
	}

}