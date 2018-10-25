package com.tx.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tx.model.Rate;
import com.tx.model.Result;

public class Utils {
	static Logger log = LoggerFactory.getLogger(Utils.class.getName());
	
	public static Result getResult(String theUrl, int id) {
		
		Result result = null;
		StringBuilder content = new StringBuilder();
		//String theUrl = "http://portal.api-core.net/api?c=102&mt=1&at=&rid=" + id;
		//http://manvip.club/portal/api?c=102&mt=1&at=&rid=32027
		//http://manvip.club/portal/api?c=102&rid=80825&mt=1&at=
		try {
			URL url = new URL(theUrl + id);

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
	/**
	 * X or T or ""
	 * 
	 * @param feed
	 * @param rate
	 * @return
	 */
	
	public static Rate findString(String buffer, String feed) {
		Rate result = new Rate();

		String feedX = feed + "X";
		String feedT = feed + "T";
		int xCount = StringUtils.countMatches(buffer, feedX);
		int tCount = StringUtils.countMatches(buffer, feedT);
		int total = xCount + tCount;
		if (xCount < JConstants.MIN || tCount < JConstants.MIN || total < JConstants.SAMPLE) {
			log.info(" Du lieu khong du so sanh " + feedX + "(" + xCount + "), " + feedT + "("+ tCount + ")");
			return result;
		}
		int xRate = (int) Math.round((xCount / (double) total) * 100);
		int tRate = (int) Math.round((tCount / (double) total) * 100);
		result = new Rate(feed, xCount, tCount, xRate, tRate);
		log.info("     Ket qua tim chuoi: " + result);
		return result;

	}
	
}
