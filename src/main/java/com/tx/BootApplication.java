package com.tx;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.tx.common.Utils;

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
		int id = 10;
		File file = new File(path + RESULT);
		String content = new String(Files.readAllBytes(file.toPath()));
		buffer = StringUtils.escape(content);
		System.out.println("\n3. Chuoi du lieu se chay");
		System.out.println("  ===== " + buffer);
		while (true) {
			log.info("Lấy kết quả từ web id [" + id + "]");
			String res = Utils.getResult(id);
			content += res;
			id++;
			if (id >= 15) {
				break;
			}
		}

		FileWriter writer = new FileWriter(path + RESULT);
		writer.write(content);
		writer.flush();
		writer.close();
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(BootApplication.class, args);
	}

}