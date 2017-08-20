package com.kevfung.jsonclass.openweatherapi;

public class Weather {
	
	String id;
	String icon;
	String description;
	String main;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMain() {
		return main;
	}

	public void setMain(String main) {
		this.main = main;
	}

	@Override
	public String toString() {
		return "Weather: [id = " + id + ", icon = " + icon + ", description = " + description + ", main = " + main + "]";
	}
}
