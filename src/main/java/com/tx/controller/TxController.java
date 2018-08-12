package com.tx.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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

	private static final String RESULT = "/result.txt";
	private static String buffer = "";
	@Value("${path}")
	private String path;
	
	@PostConstruct
	public void lastResult() throws IOException {
		File file = new File(path + RESULT);
		buffer = new String(Files.readAllBytes(file.toPath()));
	}
	
	@RequestMapping("/")
	public String config(Map<String, Object> model) {
		model.put("size", this.size);
		model.put("rate", this.rate);
		model.put("sample", this.sample);
		model.put("buffer", this.buffer);
		return "live";
	}

}