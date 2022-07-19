package com.admi.data.services;

import org.springframework.stereotype.Service;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Date;

@Service
public class DateService {

	private DateTimeFormatter formatter = new DateTimeFormatterBuilder()
												.appendPattern("yyyyMM")
												.parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
												.toFormatter();

	public long minusMonths(long dateFormat, long monthsToSubtract) {
		LocalDate date = LocalDate.parse(Long.toString(dateFormat), formatter);
		return Long.parseLong(date.minusMonths(monthsToSubtract).format(formatter));
	}

	public long minusYears(long dateFormat, long yearsToSubtract) {
		LocalDate date = LocalDate.parse(Long.toString(dateFormat), formatter);
		return Long.parseLong(date.minusYears(yearsToSubtract).format(formatter));
	}

	public long minusDays(long dateFormat, long daysToSubtract) {
		LocalDate date = LocalDate.parse(Long.toString(dateFormat), formatter);
		return Long.parseLong(date.minusDays(daysToSubtract).format(formatter));
	}

	public long getLongDate(LocalDate date) {
		return Long.parseLong(date.format(formatter));
	}

	public String getFormattedDateFromLongDate(Long date) {
		String dateString = Long.toString(date);
		String year = dateString.substring(2, 4);
		int month = Integer.parseInt(dateString.substring(4));

		String monthString = new DateFormatSymbols().getShortMonths()[month - 1];

		return monthString.concat(' ' + year);
	}

	public static String getTimeString() {
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
		Date date = new Date(System.currentTimeMillis());
		return formatter.format(date);
	}

	public static String getFileTimeString() {
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd'_'HH-mm-ss");
		Date date = new Date(System.currentTimeMillis());
		return formatter.format(date);
	}

	public static String getFileTimeString(LocalDateTime dateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'_'HH-mm-ss");
		return dateTime.format(formatter);
	}

	public DateTimeFormatter getFormatterOfPattern(String pattern) {
		return new DateTimeFormatterBuilder()
				.appendPattern(pattern)
				.toFormatter();
	}

	/**
	 * @param monthAbbreviation A month name like "Jan." or "DEC"
	 * @return The integer value of this month (Jan=1, Feb=2, etc).
	 */
	public static int getMonthFromAbbreviation(String monthAbbreviation){
		switch(monthAbbreviation.toUpperCase().replace(".","")){
			case "JAN": return 1;
			case "FEB": return 2;
			case "MAR": return 3;
			case "APR": return 4;
			case "MAY": return 5;
			case "JUN": return 6;
			case "JUL": return 7;
			case "AUG": return 8;
			case "SEP": return 9;
			case "OCT": return 10;
			case "NOV": return 11;
			case "DEC": return 12;
			default: throw new IllegalArgumentException("Could not parse " + monthAbbreviation + " into a month name.");
		}
	}

}
