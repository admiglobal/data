package com.admi.data.dto;

public class ImportIssue {
	
	private String title;
	private String type;
	private String location;
	private String message;

	public ImportIssue() {}
	
	public ImportIssue(String title, String type, String location, String message) {
		this.title = title;
		this.type = type;
		this.location = location;
		this.message = message;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ImportIssue{" +
				"title='" + title + '\'' +
				", type='" + type + '\'' +
				", location='" + location + '\'' +
				", message='" + message + '\'' +
				'}';
	}
}
