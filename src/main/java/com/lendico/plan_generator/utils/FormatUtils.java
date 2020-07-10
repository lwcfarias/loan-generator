package com.lendico.plan_generator.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class FormatUtils {

	/**
	 * Convert a string date to a Date object 
	 * @param stringDate String containing the date
	 * @return Date object
	 * @throws ParseException Exception in case wrong format.
	 */
	public static Date convertStringToDateUTC(String stringDate) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		format.setTimeZone(TimeZone.getTimeZone("UTC"));

		return format.parse(stringDate);
	}
}
