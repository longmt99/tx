package com.tx.model;

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
	public String getFeed() {
		return feed;
	}

	public void setFeed(String feed) {
		this.feed = feed;
	}

	public int getXrate() {
		return xrate;
	}

	public void setXrate(int xRate) {
		this.xrate = xRate;
	}

	public int getTrate() {
		return trate;
	}

	public void setTrate(int tRate) {
		this.trate = tRate;
	}

	public int getXcount() {
		return xcount;
	}

	public void setxCount(int xCount) {
		this.xcount = xCount;
	}

	public int getTcount() {
		return tcount;
	}

	public void setTcount(int tCount) {
		this.tcount = tCount;
	}
}
