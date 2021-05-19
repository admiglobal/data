package com.admi.data.entities.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Converter
public class ZigDateConverter implements AttributeConverter<LocalDate, String> {

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@Override
	public String convertToDatabaseColumn(LocalDate date){
		if (date != null) {
			return date.format(formatter);
		} else {
			return null;
		}
	}

	@Override
	public LocalDate convertToEntityAttribute(String dateString){
		if (dateString != null) {
			return LocalDate.parse(dateString, formatter);
		} else {
			return null;
		}
	}

}
