package com.tx.model;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tx.BootApplication;
import com.tx.common.DateUtils;
import com.tx.common.Utils;

public class TXThread implements Runnable {
	static Logger log = LoggerFactory.getLogger(TXThread.class.getName());

	private static final String RESULT = "/result.txt";
	private static String buffer = "";

	public void run() {
		try {
			String path = "/txdata";
			System.setProperty("path", path);
			log.info("Kết quả dữ liệu mới nhất lưu ở đường dẫn: " + path);
			File file = new File(path + RESULT);
			String content = new String(Files.readAllBytes(file.toPath()));
			log.info("Chuỗi dữ liệu sẽ chạy: " + content);
			String[] splits = StringUtils.split(content, ",");
			buffer = splits[0];
			int id = Integer.parseInt(splits[1]);
			++id;
			Date nextResult = DateUtils.toDate(splits[2]);

			int sleepCount = 0;
			boolean isSlept = true;
			while (true) {
				log.info("Lấy kết quả từ web id [" + id + "]");

				Result response = Utils.getResult(id);
				if (response == null) {
					if (nextResult.before(new Date())) {
						log.info("Đợi dữ liệu update  "+ sleepCount);
						Thread.sleep(3 * 1000);
						sleepCount++;
						if(sleepCount>50) {
							log.info("Không thế lấy được kết quả với id  [" + id + "] trong lần đợi " +sleepCount);
							++id;
						}
					} else if (isSlept) {
						log.info("Đợi dữ liệu update [" + sleepCount + "] 3s");
						Thread.sleep(3 * 1000);
						sleepCount++;
					} else {
						log.info("Đợi đổ xúc sắc kế tiếp [" + sleepCount + "] 30s");
						Thread.sleep(30 * 1000);
						isSlept = true;
						sleepCount++;
					}

					log.info("Sau quá trìn đợi [" + sleepCount + "] " + id);
					log.info("Kiểm tra kết quả với id " + id);
					if (sleepCount >= 50) {
						log.error(" Không thể lấy được dữ liệu trong lần thứ [" + sleepCount + "] ");
						//break;
					}
				} else {
					buffer += response.getTx();
					Date responseTime = response.getTime();
					nextResult = DateUtils.getDateAfter(responseTime, 50 + 15);
					log.info("Thời gian lấy được kết quả: " + DateUtils.toDateString(responseTime));
					log.info("Kết quả lấy vế [" + id + "] " + response.getTx() + "," + DateUtils.toDateString(responseTime));

					FileWriter writer = new FileWriter(path + RESULT);
					writer.write(buffer + "," + id + "," + DateUtils.toDateString(responseTime));
					writer.flush();
					writer.close();
					log.info("Thời gian lấy kết quả lần kế tiếp: " + DateUtils.toDateString(nextResult));
					++id;
					sleepCount = 0;
					isSlept = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
