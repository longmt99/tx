package com.tx.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tx.model.Rate;

@Controller
public class TxController {

	static Logger log = LoggerFactory.getLogger(TxController.class.getName());
	private static final String RESULT = "/result.txt";

	@PostConstruct
	public void lastResult() throws IOException {

	}

	@RequestMapping(value={"", "/", "/live"})
	public String live(Map<String, Object> model, 
			@RequestParam(required =false, defaultValue="8-9-10") String sizes,
			@RequestParam(required =false, defaultValue="80") int rate,
			@RequestParam(required =false, defaultValue="10") int sample) throws IOException {
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
			Rate xtRate = findString(buffer, input, sample);
			model.put("input" + idx, input);
			model.put("xRate" + idx, xtRate.getXrate());
			model.put("xCount" + idx, xtRate.getXcount());
			model.put("tRate" + idx, xtRate.getTrate());
			model.put("tCount" + idx, xtRate.getTcount());
		}
		model.put("id", id);
		model.put("sample", sample);
		model.put("buffer", liveBuffer);
		model.put("sizes", sizes);
		model.put("time", splits[2]);
		
		
	
		return "live";
	}
	
	/**
	 * X or T or ""
	 * 
	 * @param feed
	 * @param rate
	 * @return
	 */
	static final int MIN =2;
	public static Rate findString(String buffer, String feed, int sample) {
		Rate result = new Rate();

		String feedX = feed + "X";
		String feedT = feed + "T";
		int xCount = StringUtils.countMatches(buffer, feedX);
		int tCount = StringUtils.countMatches(buffer, feedT);
		int total = xCount + tCount;
		if (xCount < MIN || tCount < MIN || total < sample) {
			System.out.println("      Du lieu khong du so sanh " + feedX + "(" + xCount + "), " + feedT + "("+ tCount + ")");
			return result;
		}
		int xRate = (int) Math.round((xCount / (double) total) * 100);
		int tRate = (int) Math.round((tCount / (double) total) * 100);
		result = new Rate(feed, xCount, tCount, xRate, tRate);
		System.out.println("     Ket qua tim chuoi: " + result);
		return result;

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

}