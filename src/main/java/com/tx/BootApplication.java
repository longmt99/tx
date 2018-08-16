package com.tx;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.tx.common.DateUtils;
import com.tx.common.Utils;
import com.tx.model.Result;

@SpringBootApplication
public class BootApplication {
	static Logger log = LoggerFactory.getLogger(BootApplication.class.getName());
	private static final String INPUT = "input";
	private static final String CONFIG = "config.txt";
	private static final String OUTPUT = "output.json";

	private static final String RESULT = "/result.txt";
	private static String buffer = "";
	@Value("${path}")
	private String path;

	@PostConstruct
	public void init() throws Exception {
		log.info("Update kết quả dữ liệu mới nhất " + path);
		int id = 31500;
		File file = new File(path + RESULT);
		String result = new String(Files.readAllBytes(file.toPath()));
		buffer = StringUtils.escape(result);
		log.info("\n3. Chuoi du lieu se chay");
		log.info("  ===== " + buffer);
		log.info("Lấy kết quả từ web id [" + id + "]");
		int sleepCount = 0;
		while (true) {
			Result response = Utils.getResult(id);
			if(response==null) {
				log.info(" continue id " +id);
				continue;
			}
			result += response;
			id++;
			Date before = DateUtils.getDateBefore(new Date(), 80);
			Date responseTime = response.getTime();
			if(responseTime.after(before)){
				log.info("In sleep count [" + sleepCount + "] ");
				Thread.sleep(5*1000);
				++sleepCount;
				log.info("Out sleep count [" + sleepCount + "] ");
			}else {
				log.info("GOT [" + id + "] " + response.getTx() +"," + DateUtils.toDateString(responseTime));
				sleepCount = 0;
			}
			if(sleepCount>=20) {
				log.error(" NO RESPONSE in sleep count [" + sleepCount + "] ");
				break;
			}
		}

		FileWriter writer = new FileWriter(path + RESULT);
		writer.write(result);
		writer.flush();
		writer.close();
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(BootApplication.class, args);
	}

}