package com.kevfung.jsonclass;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentWeather {
	   
	Weather[] weather;
	Main main;	
	String name;
	long dt;

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

	public long getDt() {
		return dt;
	}

	public void setDt(long dt) {
		this.dt = dt;
	}
	
	public String getFormattedDt() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
		return dateFormat.format(new Date(dt));
	}

	@Override
	public String toString() {
		StringBuffer weatherStrBuffer = new StringBuffer();
		for(int weatherItem = 0; weatherItem < weather.length; weatherItem++) {
			weatherStrBuffer.append(weatherItem + " " + weather[weatherItem].toString() + "\n");
		}			
		
		return "Current Weather [name : " + name + "\n"
				+ "date : " + DateFormatUtils.ISO_DATETIME_FORMAT.format(new Date(dt)) + "\n"
				+ weatherStrBuffer
				+ main + "\n"
				+ "]";
	}
}
