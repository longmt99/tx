package com.tx.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tx.common.JConstants;
import com.tx.common.Utils;
import com.tx.model.Rate;
import com.tx.model.Result;

@Controller
public class OutputController {

	static Logger log = LoggerFactory.getLogger(OutputController.class.getName());
	private static final String RESULT = "/result.txt";
	
	private static Map<String, Rate> output = new HashMap<String, Rate>();
	
	@RequestMapping(value={"/output"})
	public String out1(Map<String, Object> model, 
			@RequestParam(required =false, defaultValue="8") int size,
			@RequestParam(required =false, defaultValue="40") int rate
			) throws Exception {
		String path = System.getProperty("path");
		if (path == null) {
			path = "/txdata";
		}
		File file = new File(path + RESULT);
		String content = new String(Files.readAllBytes(file.toPath()));
		String[] splits = StringUtils.split(content, ",");
		String buffer = splits[0];
		log.debug("\n3. Chuoi du lieu se chay");
		log.debug("  ===== " + buffer);
		int start = 0;
		int end = 0;
		log.info("\n4. Bat dau chay du lieu ");
			while (end <= buffer.length() - 2) {
				end = start + size - 1;
				String feed = buffer.substring(start, end);
				if (output.containsKey(feed)) {
					// Have existed
					++start;
					continue;
				}
				log.info("\n  ===== Chuoi thu ban dau: " + feed);
				Rate result = Utils.findString(buffer,feed);
				if (result != null) {
					output.put(result.getFeed(), result);
				}
				++start;
			}
			
		List<Result> result= storeOutput(path,rate);
		
		int type = JConstants.HIGHER;
		String title = "Lọc dữ liệu có độ dài " + size +" và tỉ lệ"; 
		if (rate < 50) {
			type = JConstants.UNDER;
			title+=" nhỏ hơn " +rate;
		}else{
			title+=" lớn hơn " +rate;
		}
		String OUTPUT = path + "/output" + type + ".json";
		
		model.put("size", size);
		model.put("rate", rate);
		model.put("time", splits[2]);
		model.put("title", title);
		model.put("path", OUTPUT);
		model.put("output", result);
		
		return "output";
	}
	
	@SuppressWarnings("unchecked")
	private static List<Result> storeOutput(String path, int rate ) throws Exception {
		JSONObject obj = new JSONObject();
		// String general
		// ="{'files':'','input-1':'-0','input-2':'0','input-3':'-1','input-4':'0','input-5':false,'input-6':'5','input-7':'5','input-8':true,'input-9':'1','input-10':true,'input-11':'2','input-12':true,'input-13':'3','input-14':true,'input-15':'4','input-16':true,'input-17':'5','manyMoney':false,'lessMoney':true,'manyPerson':false,'lessPerson':false,'lessMoneyManyPerson':false,'lessMoneyLessPerson':false,'number-xucxac-tai':'0,1,5,9','number-xucxac-xiu':'3,4,6,7','endtongxucxac':true,'xucxac1':false,'xucxac2':false,'xucxac3':false,'tongxucxac12':false,'tongxucxac13':false,'tongxucxac23':false,'tongxucxac123':false,'input-100':'(1-3-6),(4-4-6),(1-1-5,2-3-6),(3-5-6,6-6-6)','input-101':'(1-1-1,1-1-2),(1-2-3),(1-2-4,3-4-6),(2-3-5),(1-3-5)','input-18':'5000','input-19':'8','input-20':'99','input-21':'99','guessTo':'95','guessFrom':'55','input-22':false,'input-23':'1','input-24':'1','input-25':false,'undefined':'','input-30':false,'input-31':false,'cStringCustom':'TNXGT?T-XXTT','input-32':false,'input-33':'10','input-34':'1000','input-35':true,'input-36':false,'input-37':'10','input-38':'1','input-39':'100.000','input-102':'100000,100000,100000,100000,100000,100000,100000,100000,100000,100000,100000,100000,100000,100000,100000,100000,100000,100000,100000,100000,100000,100000,100000,100000,100000','input-40':false,'input-41':'15','input-42':'10','input-43':'0-0','input-44':'0-0','input-45':'99','input-46':false,'input-47':false,'input-48':'10','input-49':'2','input-50':'1.000','input-103':'1000,2100,4200,8500,17200,34700,70200,141800,286500,578800,1169400,2362700,4773600,9644600,19486100,39369800,79543100,160709500,324698800,656024100,1325436500,2677922700,5410496900,10931412100,22085914200','input-51':'10','input-52':'0-0','loaiCau':'11'}";
		JSONObject generalObj = new JSONObject();
		generalObj.put("files", "");
		generalObj.put("input-1", "-0");
		generalObj.put("input-2", "0");
		generalObj.put("input-3", "-1");
		generalObj.put("input-4", "0");
		generalObj.put("input-5", false);
		generalObj.put("input-6", "5");
		generalObj.put("input-7", "5");
		generalObj.put("input-8", true);
		generalObj.put("input-9", "1");
		generalObj.put("input-10", true);
		generalObj.put("input-11", "2");
		generalObj.put("input-12", true);
		generalObj.put("input-13", "3");
		generalObj.put("input-14", true);
		generalObj.put("input-15", "4");
		generalObj.put("input-16", true);
		generalObj.put("input-17", "5");
		generalObj.put("manyMoney", false);
		generalObj.put("lessMoney", true);
		generalObj.put("manyPerson", false);
		generalObj.put("lessPerson", false);
		generalObj.put("lessMoneyManyPerson", false);
		generalObj.put("lessMoneyLessPerson", false);
		generalObj.put("number-xucxac-tai", "0,1,5,9");
		generalObj.put("number-xucxac-xiu", "3,4,6,7");
		generalObj.put("endtongxucxac", true);
		generalObj.put("xucxac1", false);
		generalObj.put("xucxac2", false);
		generalObj.put("xucxac3", false);
		generalObj.put("tongxucxac12", false);
		generalObj.put("tongxucxac13", false);
		generalObj.put("tongxucxac23", false);
		generalObj.put("tongxucxac123", false);
		generalObj.put("input-100", "(1-3-6),(4-4-6),(1-1-5,2-3-6),(3-5-6,6-6-6)");
		generalObj.put("input-101", "(1-1-1,1-1-2),(1-2-3),(1-2-4,3-4-6),(2-3-5),(1-3-5)");
		generalObj.put("input-18", "5000");
		generalObj.put("input-19", "8");
		generalObj.put("input-20", "99");
		generalObj.put("input-21", "99");
		generalObj.put("guessTo", "95");
		generalObj.put("guessFrom", "55");
		generalObj.put("input-22", false);
		generalObj.put("input-23", "1");
		generalObj.put("input-24", "1");
		generalObj.put("input-25", false);
		generalObj.put("undefined", "");
		generalObj.put("input-30", false);
		generalObj.put("input-31", false);
		generalObj.put("cStringCustom", "TNXGT?T-XXTT");
		generalObj.put("input-32", false);
		generalObj.put("input-33", "10");
		generalObj.put("input-34", "1000");
		generalObj.put("input-35", true);
		generalObj.put("input-36", false);
		generalObj.put("input-37", "10");
		generalObj.put("input-38", "1");
		generalObj.put("input-39", "100.000");
		generalObj.put("input-102",
				"100000,100000,100000,100000,100000,100000,100000,100000,100000,100000,100000,100000,100000,100000,100000,100000,100000,100000,100000,100000,100000,100000,100000,100000,100000");
		generalObj.put("input-40", false);
		generalObj.put("input-41", "15");
		generalObj.put("input-42", "10");
		generalObj.put("input-43", "0-0");
		generalObj.put("input-44", "0-0");
		generalObj.put("input-45", "99");
		generalObj.put("input-46", false);
		generalObj.put("input-47", false);
		generalObj.put("input-48", "10");
		generalObj.put("input-49", "2");
		generalObj.put("input-50", "1.000");
		generalObj.put("input-103",	"1000,2100,4200,8500,17200,34700,70200,141800,286500,578800,1169400,2362700,4773600,9644600,19486100,39369800,79543100,160709500,324698800,656024100,1325436500,2677922700,5410496900,10931412100,22085914200");
		generalObj.put("input-51", "10");
		generalObj.put("input-52", "0-0");
		generalObj.put("loaiCau", "11");
		obj.put("general", generalObj);

		JSONArray nangCao = new JSONArray();
		List<Result> goodResult = new ArrayList<Result>();
		int type = JConstants.HIGHER;
		if (rate < 50) {
			type = JConstants.UNDER;
		}
		String OUTPUT = "/output" + type + ".json";
		int found = 0;
		for (Map.Entry<String, Rate> entry : output.entrySet()) {
			Rate result = entry.getValue();
			if(type==2){
				if (result.getXrate() >= rate) {
					found++;
					nangCao.add(result.getFeed() + "-X");
					log.info("  " + found + "." + result);
					Result out = new Result(result.getFeed() + "-X",result.getXrate());
					goodResult.add(out);
				} else if (result.getTrate() >= rate) {
					found++;
					nangCao.add(result.getFeed() + "-T");
					log.info("  " + found + "." + result);
					Result out = new Result(result.getFeed() + "-T",result.getTrate());
					goodResult.add(out);
				}
			}else if(type==1){
				if (result.getXrate() <= rate) {
					found++;
					nangCao.add(result.getFeed() + "-X");
					log.info("  " + found + "." + result);
					Result out = new Result(result.getFeed() + "-X",result.getXrate());
					goodResult.add(out);
				} else if (result.getTrate() <= rate) {
					found++;
					nangCao.add(result.getFeed() + "-T");
					log.info("  " + found + "." + result);
					Result out = new Result(result.getFeed() + "-T",result.getTrate());
					goodResult.add(out);
				}
			}
		}
		obj.put("nangCao", nangCao);
		
		
		log.info("\n5. Loc ket qua du lieu OUTPUT data " + OUTPUT);
		try (FileWriter file = new FileWriter(path+ OUTPUT )) {
			file.write(obj.toJSONString());
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return goodResult;
	}
	
}