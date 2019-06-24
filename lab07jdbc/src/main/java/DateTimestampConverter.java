package main.java;

import java.sql.Timestamp;
import java.util.Date;

public class DateTimestampConverter {

	public static Date convert(java.sql.Date date) {
		return new Date(date.getTime());
	}

	public static java.sql.Date convert(Date date) {
		return new java.sql.Date(date.getTime());
	}

	public static Date convert(Timestamp timestamp) {
		return new Date(timestamp.getTime());
	}
}