package com.admi.data.dto;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class CellDefinition<T, V, W> {

	private CellType cellType;
	private Function<Cell, W> cellGetter;
	private Class<?> getterClass;
	private BiConsumer<T, V> entitySetter;
	private Class<?> setterClass;

	public CellDefinition(){}

	public CellDefinition(CellType cellType,
	                      Function<Cell, W> cellGetter,
						  Class<?> getterClass,
	                      BiConsumer<T, V> entitySetter,
	                      Class<?> setterClass) {
		this.cellType = cellType;
		this.cellGetter = cellGetter;
		this.entitySetter = entitySetter;
		this.getterClass = getterClass;
		this.setterClass = setterClass;
	}

	public W getValue(Cell cell) {
		return cellGetter.apply(cell);
	}

	public void setField(T entity, V value) {
		entitySetter.accept(entity, value);
	}

	public void getAndSetField(Cell cell, T entity) {
		try {

			W w = cellGetter.apply(cell);
			V value = convert(w);
			entitySetter.accept(entity, value);

		} catch (IllegalStateException e) {
			System.out.println(e.getMessage());
			System.out.println("Cell Value: " + cell.toString());
			System.out.println("Cell Row: " + cell.getRowIndex());
			System.out.println("Cell Column: " + cell.getColumnIndex());
			System.out.println(this.toString());
		}
	}

	public CellType getCellType() {
		return cellType;
	}

	public void setCellType(CellType cellType) {
		this.cellType = cellType;
	}

	public Class<?> getGetterClass() {
		return getterClass;
	}

	public void setGetterClass(Class<?> getterClass) {
		this.getterClass = getterClass;
	}

	public Class<?> getSetterClass() {
		return setterClass;
	}

	public void setSetterClass(Class<?> setterClass) {
		this.setterClass = setterClass;
	}

	public Function<Cell, W> getCellGetter() {
		return cellGetter;
	}

	public void setCellGetter(Function<Cell, W> cellGetter) {
		this.cellGetter = cellGetter;
	}

	public BiConsumer<T, V> getEntitySetter() {
		return entitySetter;
	}

	public void setEntitySetter(BiConsumer<T, V> entitySetter) {
		this.entitySetter = entitySetter;
	}

	private V convert(W toConvert) {
		V newValue = null;

		if (getterClass == setterClass) {
			newValue = (V) toConvert;
		}

		if (LocalDateTime.class.equals(getterClass) && LocalDate.class.equals(setterClass)) {
			LocalDateTime time = (LocalDateTime) toConvert;
			newValue = (V) LocalDate.of(time.getYear(), time.getMonth(), time.getDayOfMonth());
		}

		if (Double.class.equals(getterClass) && Integer.class.equals(setterClass)) {
			Double d = (Double) toConvert;
			Integer i = d.intValue();
			newValue = (V) i;
		}

		return newValue;
	}

	@Override
	public String toString() {
		return "CellDefinition{" +
				"cellGetter=" + cellGetter +
				", getterClass=" + getterClass +
				", entitySetter=" + entitySetter +
				", setterClass=" + setterClass +
				'}';
	}
}
