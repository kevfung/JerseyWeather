package com.kevfung.utils.jsonclasses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentWeather {
	   
	Weather[] weather;
	Main main;	
	String name;

	public Weather[] getWeather() {
		return weather;
	}

	public void setWeather(Weather[] weather) {
		this.weather = weather;
	}

	public Main getMain() {
		return main;
	}

	public void setMain(Main main) {
		this.main = main;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		StringBuffer weatherStrBuffer = new StringBuffer();
		for(int weatherItem = 0; weatherItem < weather.length; weatherItem++) {
			weatherStrBuffer.append(weatherItem + " " + weather[weatherItem].toString() + "\n");
		}
		return "Current Weather [name : " + name + "\n"
				+ weatherStrBuffer
				+ main + "\n"
				+ "]";
	}
}
