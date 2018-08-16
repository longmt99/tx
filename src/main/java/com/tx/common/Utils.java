package com.tx.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.Date;

import org.json.JSONObject;

import com.tx.model.Result;

public class Utils {
	public static Result getResult(int id) {

		Result result = null;
		StringBuilder content = new StringBuilder();
		String theUrl = "http://portal.api-core.net/api?c=102&mt=1&at=&rid=" + id;
		try {
			URL url = new URL(theUrl);

			URLConnection urlConnection = url.openConnection();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			String line;

			// read from the urlconnection via the bufferedreader
			while ((line = bufferedReader.readLine()) != null) {
				content.append(line);
			}
			bufferedReader.close();
			JSONObject parser = new JSONObject(content.toString());
			if (parser.isNull("resultTX")) {
				return null;
			} else {
				JSONObject resultTX = parser.getJSONObject("resultTX");
				/*
				 * resultTX: { referenceId: 9206, result: 0, dice1: 1, dice2: 4, dice3: 5,
				 * totalTai: 26674802, totalXiu: 27669882, numBetTai: 0, numBetXiu: 0,
				 * totalPrize: 52816080, totalRefundTai: 0, totalRefundXiu: 995080,
				 * totalRevenue: 0, moneyType: 1, timestamp: "22:04:29 26/07/2018" }
				 */
				int dice1 = (int) resultTX.get("dice1");
				int dice2 = (int) resultTX.get("dice2");
				int dice3 = (int) resultTX.get("dice3");
				String timestamp = (String) resultTX.get("timestamp");
				String tx = (dice1 + dice2 + dice3) < 11 ? "X" : "T";
				Date time = DateUtils.toDate(timestamp);
				result = new Result(tx, time);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	public static void main(String[] args) {

	}

	/**
	 * String fileName = "result.txt";
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static String readFile(String fileName) throws IOException {

		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());

		// File is found
		System.out.println("File Found : " + file.exists());

		// Read File Content
		String content = new String(Files.readAllBytes(file.toPath()));
		System.out.println(content);
		return content;
	}
}
