package com.tx.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result {
	private String tx;
	private Date time;

	public Result() {

	}

	public Result(String tx, Date time) {
		this.tx = tx;
		this.time = time;
	}

	public String toString() {
		return "tx [" + tx + "]     time {" + time + "]";
	}
}
