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
	 * Parses a long of the format mddyy into a LocalDate. Assumes that the yy field
	 * represents a date between the years 1990 and 2090.
	 * If the argument is null, returns a null LocalDate.
	 * @param longDate A Long number representing a date of the format mddyy,
	 *                 for example "32717" represents March 27, 2017 and
	 *                 "110321" is November 3, 2021.
	 * @return this date as a LocalDate object
	 */
	public static LocalDate parseLongAsDate(Long longDate){
		if(longDate == null) { return null; }

		int truncYear = (int) (longDate % 100);
		int day = (int) (longDate % 10000) / 100;
		int month = (int) (longDate - truncYear - day) / 10000;

		int year = 2000 + truncYear;
		if(year > 2090) {
			year -= 100; //reset to the 1990's
		}

		return LocalDate.of(year, month, day);
	}


}
