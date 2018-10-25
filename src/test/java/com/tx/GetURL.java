package com.tx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class GetURL {

	public static void main(String[] args) throws IOException {
		System.setProperty("http.agent", "Chrome");
		StringBuilder content = new StringBuilder();
		String theUrl = "http://manvip.club/portal/api?c=102&rid=80868&mt=1&at=";
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
			System.out.println(content);
		}catch (Exception e) {
			e.printStackTrace();
		}	
		
	}

}
