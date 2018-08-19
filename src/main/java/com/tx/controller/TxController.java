package com.tx.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tx.common.Utils;
import com.tx.model.Rate;

@Controller
public class TxController {

	static Logger log = LoggerFactory.getLogger(TxController.class.getName());
	private static final String RESULT = "/result.txt";


	@RequestMapping(value={"", "/", "/live"})
	public String live(Map<String, Object> model, 
			@RequestParam(required =false, defaultValue="8-9-10") String sizes			
			) throws IOException {
		String path = System.getProperty("path");
		if (path == null) {
			path = "/txdata";
		}
		File file = new File(path + RESULT);
		String content = new String(Files.readAllBytes(file.toPath()));
		String[] splits = StringUtils.split(content, ",");
		String buffer = splits[0];
		int id = Integer.parseInt(splits[1]);
		++id;
		String[] sz = StringUtils.split(sizes, "-");
		String liveBuffer = buffer.substring(buffer.length() - 22);
		for (int idx = 0; idx < sz.length; idx++) {
			int size = Integer.parseInt(sz[idx]);
			model.put("size" + idx, sz[idx]);
			String input = buffer.substring(buffer.length() - size + 1);
			Rate xtRate = Utils.findString(buffer, input);
			model.put("input" + idx, input);
			model.put("xRate" + idx, xtRate.getXrate());
			model.put("xCount" + idx, xtRate.getXcount());
			model.put("tRate" + idx, xtRate.getTrate());
			model.put("tCount" + idx, xtRate.getTcount());
		}
		model.put("id", id);
		model.put("buffer", liveBuffer);
		model.put("sizes", sizes);
		model.put("time", splits[2]);
	
		return "live";
	}
	
	

	@RequestMapping("/tx")
	public String tx(Map<String, Object> model) throws IOException {
		String path = System.getProperty("path");
		if (path == null) {
			path = "/txdata";
		}
		File file = new File(path + RESULT);
		String content = new String(Files.readAllBytes(file.toPath()));
		String[] splits = StringUtils.split(content, ",");
		String buffer = splits[0];
		int id = Integer.parseInt(splits[1]);
		++id;
		int length = 80;
		int start = 0;
		int end = length;
		String lineBuffer = "";
		for (int line = 0;; ++line) {
			start = line * length;
			end = (line + 1) * length;
			if (end < buffer.length()) {
				lineBuffer += " " + buffer.substring(start, end) ;
			} else {
				lineBuffer += " " + buffer.substring(start) ;
				break;
			}
		}
		model.put("buffer", lineBuffer);
		model.put("id", id);
		model.put("time", splits[2]);
		return "tx";
	}
	
	@RequestMapping("/help")
	public String help(Map<String, Object> model) throws IOException {
		String path = System.getProperty("path");
		if (path == null) {
			path = "/txdata";
		}
		return "help";
	}

}