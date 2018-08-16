package com.tx.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Rate {
	private String feed;
	private int xrate;
	private int trate;
	private int xcount;
	private int tcount;

	public Rate() {

	}

	public Rate(String feed,int xCount, int tCount, int xRate, int tRate) {
		this.feed = feed;
		this.xcount = xCount;
		this.tcount = tCount;
		this.xrate = xRate;
		this.trate = tRate;
	}
	
	public String toString() {
		return "Chuoi thu ban dau [" + feed + 
				"]\n     Chuoi ket qua X-T tuong ung {" + feed + "X - " + feed + "T}\n"
				 + "     So lan xuat hien [" + xcount + " - " + tcount + "]\n" 
				 + "     Tỉ le  [" + xrate + " - "		+ trate + "]\n";
	}	
	public String toOuput() {
		return "Input " + feed + " ---> {" + feed + "X - " + feed + "T} --> [" + xcount + " - " + tcount + "] --> [" + xrate + " - "		+ trate + "]";
	}	
}
