package com.tx;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import com.tx.model.Rate;

public class App {
	private static final String INPUT = "input";
	private static final String CONFIG = "config.txt";
	private static final String OUTPUT = "output.json";
	private static int size = 14;
	private static int rate = 70;
	private static int sample = 8;
	private static int min = 2;
	private static String buffer = "";
	private static Map<String, Rate> output = new HashMap<String, Rate>();

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {
		System.out.println("==============================================================================");
		System.out.println("||                              PHAN TICH DU LIEU XT                        ||");
		System.out.println("||                                       + Tong hop du lieu output          ||");
		System.out.println("||                                       + Phan tich chuoi XT theo input    ||");
		System.out.println("==============================================================================");
		System.out.println("||            1.0+ $java -jar xt.jar                                        ||");
		System.out.println("||            2.1+ $java -jar xt.jar XXXX                                   ||");
		System.out.println("||            2.2+ $java -jar xt.jar XXXX 65                                ||");
		System.out.println("==============================================================================\n");
		loadConfig();
		System.out.println("1. Nap cau hinh config ");
		if (args.length >= 1) {
			size = args[0].length();
		}
		if (args.length == 2) {
			rate = Integer.parseInt(args[1]);
		}
		System.out.println("\n    DO DAI CHUOI = [" + size + "]\n    TI LE CHAP NHAN = [" + rate + "]");

		buffer = "";
		List<String> list = listData(new File(INPUT));
		System.out.println("\n2. Du lieu dau vao input data " + list + "");
		for (String name : list) {
			buffer += readFile(INPUT + File.separator + name);
		}
		buffer = StringUtils.escape(buffer);
		System.out.println("\n3. Chuoi du lieu se chay");
		System.out.println("  ===== " + buffer);
		int start = 0;
		int end = 0;
		System.out.println("\n4. Bat dau chay du lieu ");
		if (args.length < 1) {
			while (end <= buffer.length() - 2) {
				end = start + size - 1;
				String feed = buffer.substring(start, end);
				if (output.containsKey(feed)) {
					// Have existed
					++start;
					continue;
				}
				System.out.println("\n  ===== Chuoi thu ban dau: " + feed);
				Rate result = findString(feed);
				if (result != null) {
					output.put(result.getFeed(), result);
				}
				++start;
			}
			System.out.println("\n5. Loc ket qua du lieu OUTPUT data " + OUTPUT);
			storeOutput();
		} else {
			String feed = args[0];
			System.out.println("\n  ===== Chuoi thu ban dau: " + feed);
			Rate result = findString(feed);
			System.out.println("\n5. Phan tich chuoi XT theo input " + feed);
			System.out.println("     Ket qua ");
			if (result != null && (result.getXrate() >= rate || result.getTrate() >= rate)) {
				System.out.print("     " + result);
			}
		}

	}

	private static void storeOutput() throws Exception {
		List<String> nangCao = new ArrayList<String>();
		int found = 0;
		for (Map.Entry<String, Rate> entry : output.entrySet()) {
			Rate result = entry.getValue();
			if (result.getXrate() >= rate) {
				found++;
				nangCao.add(result.getFeed() + "-X");
				System.out.println("  " + found + "." + result);
			} else if (result.getTrate() >= rate) {
				found++;
				nangCao.add(result.getFeed() + "-T");
				System.out.println("  " + found + "." + result);
			}
		}


		try (FileWriter file = new FileWriter(OUTPUT)) {
			file.write(nangCao.toString());
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * X or T or ""
	 * 
	 * @param feed
	 * @param rate
	 * @return
	 */
	public static Rate findString(String feed) {
		Rate result = null;

		String feedX = feed + "X";
		String feedT = feed + "T";
		int xCount = StringUtils.countMatches(buffer, feedX);
		int tCount = StringUtils.countMatches(buffer, feedT);
		int total = xCount + tCount;
		if (xCount < min || tCount < min || total < sample) {
			System.out.println("      Du lieu khong du so sanh " + feedX + "(" + xCount + "), " + feedT + "("+ tCount + ")");
			return result;
		}
		int xRate = xCount * 100 / total;
		int tRate = tCount * 100 / total;
		result = new Rate(feed, xCount, tCount, xRate, tRate);
		System.out.println("     Ket qua tim chuoi: " + result);
		return result;

	}

	public static void loadConfig() {
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(CONFIG);
			prop.load(input);
			size = Integer.parseInt(prop.getProperty("size"));
			rate = Integer.parseInt(prop.getProperty("rate"));
			min=Integer.parseInt(prop.getProperty("min"));
			sample= Integer.parseInt(prop.getProperty("sample"));
		} catch (Exception ex) {
			System.out.println("Get DEFAULT config");
		}
	}

	public static List<String> listData(final File folder) {
		List<String> list = new ArrayList<>();
		for (final File file : folder.listFiles()) {
			String name = file.getName();
			list.add(name);
		}
		return list;
	}

	public static String readFile(String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}
			return sb.toString();
		} finally {
			br.close();
		}
	}
}
