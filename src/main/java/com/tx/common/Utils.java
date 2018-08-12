package com.tx.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Utils {
	public static String getResult(int id) {
		if (id % 2 == 0) {
			return "T";
		} else {
			return "X";
		}
	}
	/**
	 * String fileName = "result.txt";
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static String readFile(String fileName) throws IOException {
		
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());
		 
		//File is found
		System.out.println("File Found : " + file.exists());
		 
		//Read File Content
		String content = new String(Files.readAllBytes(file.toPath()));
		System.out.println(content);
		return content;
	}
}
