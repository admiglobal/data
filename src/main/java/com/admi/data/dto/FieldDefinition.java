package com.admi.data.dto;

import org.apache.poi.ss.usermodel.CellType;

import java.util.function.BiConsumer;

public class FieldDefinition<T, V> {

	private CellType cellType;
	private Class<?> clazz;
	private BiConsumer<T, V> setter;

	public FieldDefinition(){}

	public FieldDefinition(CellType cellType,
	                       Class<?> clazz,
	                       BiConsumer<T, V> setter) {
		this.cellType = cellType;
		this.clazz = clazz;
		this.setter = setter;
	}

	public void setField(T dto, V v) {
		if (v.getClass() == clazz) {
			setter.accept(dto, v);
		} else {
			System.out.println("Types don't match.");
		}
	}

	public CellType getCellType() {
		return cellType;
	}

	public void setCellType(CellType cellType) {
		this.cellType = cellType;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}

	public BiConsumer<T, V> getSetter() {
		return setter;
	}

	public void setSetter(BiConsumer<T, V> setter) {
		this.setter = setter;
	}
}
