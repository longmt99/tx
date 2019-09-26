package com.tx.controller;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tx.common.DateUtils;
import com.tx.common.Utils;
import com.tx.model.Result;

public class TXThread implements Runnable {
	static Logger log = LoggerFactory.getLogger(TXThread.class.getName());

	private static final String RESULT = "/result.txt";
	private static String buffer = "";
	private String url; 

	public TXThread(String url) {
		this.url = url;
	}

	public void run() {
		try {
			String path = "/txdata";
			System.setProperty("path", path);
			log.info("Ket qua du lieu moi nhat luu o duong dan: " + path);
			File file = new File(path + RESULT);
			String content = new String(Files.readAllBytes(file.toPath()));
			log.info("Chuoi du lieu se chay: " + content);
			String[] splits = StringUtils.split(content, ",");
			buffer = splits[0];
			int id = Integer.parseInt(splits[1]);
			++id;
			Date nextResult = DateUtils.toDate(splits[2]);

			int sleepCount = 0;
			boolean isSlept = true;
			while (true) {
				log.info("Lay ket qua tu web id [" + id + "]");

				Result response = Utils.getResult(url, id);
				if (response == null) {
					if (nextResult.before(new Date())) {
						log.info("Doi du lieu update  "+ sleepCount);
						Thread.sleep(3 * 1000);
						sleepCount++;
						if(sleepCount>50) {
							log.info("Khong the lay duoc ket qua voi id  [" + id + "] trong lan doi " +sleepCount);
							++id;
						}
					} else if (isSlept) {
						log.info("Doi du lieu update [" + sleepCount + "] 3s");
						Thread.sleep(3 * 1000);
						sleepCount++;
					} else {
						log.info("Slepping phien lam viec tiep theo [" + sleepCount + "] 30s");
						Thread.sleep(30 * 1000);
						isSlept = true;
						sleepCount++;
					}

					log.info("Sau qua trinh doi [" + sleepCount + "] " + id);
					log.info("Kiem tra ket qua voi id " + id);
					if (sleepCount >= 50) {
						log.error(" Khong the lay duoc du lieu trong lan thu [" + sleepCount + "] ");
						//break;
					}
				} else {
					buffer += response.getTx();
					Date responseTime = response.getTime();
					nextResult = DateUtils.getDateAfter(responseTime, 50 + 15);
					log.info("Thoi gian lay duoc ket qua: " + DateUtils.toDateString(responseTime));
					log.info("Ket qua lay ve [" + id + "] " + response.getTx() + "," + DateUtils.toDateString(responseTime));

					FileWriter writer = new FileWriter(path + RESULT);
					writer.write(buffer + "," + id + "," + DateUtils.toDateString(responseTime));
					writer.flush();
					writer.close();
					log.info("Thoi gian lay ket qua lan ke tiep: " + DateUtils.toDateString(nextResult));
					++id;
					sleepCount = 0;
					isSlept = false;
					Thread.sleep(350);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
