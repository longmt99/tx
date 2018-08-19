package com.tx.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result {
	private String tx;
	private Date time;
	private int rate;
	private int xcount;
	private int tcount;
	public Result() {

	}

	public Result(String tx, Date time) {
		this.tx = tx;
		this.time = time;
	}

	public Result(String tx, int rate) {
		this.tx = tx;
		this.rate = rate;
	}

	public String toString() {
		return "tx [" + tx + "]     time {" + time + "]";
	}
}
