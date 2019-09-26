package com.tx;import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.tomcat.util.codec.binary.Base64;



public class HttpBasicAuth {

    public static void main(String[] args) {
    	System.setProperty("http.agent", "Chrome");
        try {
            URL url = new URL ("http://minigame.m88.vin/api/LuckyDice/GetStatitics");

            //Base64 b = new Base64();
            //String encoding = "";//b.encodeAsString(new String("longmt99:123456789").getBytes());

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Length", "0");
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty  ("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1laWQiOiIxNDU4MTEyIiwidW5pcXVlX25hbWUiOlsie1wiQWNjb3VudElEXCI6MTQ1ODExMixcIlVzZXJuYW1lXCI6XCJsb25nbXQ5OVwiLFwiTmlja25hbWVcIjpcIltNODhdbG9uZ3Rlc3RcIixcIkF2YXRhcklEXCI6XCIwXCIsXCJTZXNzaW9uVG9rZW5cIjpcImU5NmM5OTU4OTk2ZDI2NzAzZGQxZmM5MWU5NTA5ZWJlXCIsXCJTZWN1cml0eVRva2VuXCI6XCIxNTU4MTI4NzQ0LjVkOTg3MzFkMGUyZjQ2OTM3OTZjOGY5MmNmMDAzZjQ2XCIsXCJTb3VyY2VJRFwiOjEsXCJDbGllbnRJUFwiOlwiMTEzLjE2Mi44Ni4xMzhcIixcIlBvcnRhbElEXCI6MixcIk9yaWdpbk5pY2tuYW1lXCI6XCJsb25ndGVzdFwiLFwiTG9naW5EYXRlXCI6XCIwMDAxLTAxLTAxVDAwOjAwOjAwXCJ9IiwibG9uZ210OTkiXSwiSGFzQWRtaW5SaWdodHMiOiJOIiwic3ViIjoibG9uZ210OTkiLCJqdGkiOiJmNjE4YWIyOC0wMjdhLTQ3OGYtOGZkMC1hMjNiNzhiZjQzZTQiLCJpYXQiOjE1NTgxMDM2MzksIm5iZiI6MTU1ODEwMzYzOSwiZXhwIjoxNTU4MTIxNjM5LCJpc3MiOiJ3ZWJBcGkiLCJhdWQiOiJodHRwOi8vZ2FtdmlwLmNvbS8ifQ.3QhoqjIMq_8Gncn0dq_NbyKXslM4ifBs1d0zwSMVLew");
            InputStream content = (InputStream)connection.getInputStream();
            BufferedReader in   = 
                new BufferedReader (new InputStreamReader (content));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } 
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}