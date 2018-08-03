package com.tx;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;

public class Test {

	public static void main(String[] args) throws IOException {
		String content = FileUtils.readFileToString(new File("input.txt"),Charset.defaultCharset());
		System.out.println(content.length());
	}

}
