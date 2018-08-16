package com.tx;

import java.io.IOException;
import java.util.Date;

import com.tx.common.DateUtils;

public class Test {

	public static void main(String[] args) throws IOException {
		
		//String content = FileUtils.readFileToString(new File("input.txt"),Charset.defaultCharset());
		//System.out.println(content.length());
		
		Date now = new Date();
		System.out.println(DateUtils.toDateString(now));
		
		System.out.println(DateUtils.toDateString(DateUtils.getDateAfter(now, 100)));
		
	}

}
