package com.kevfung.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.log4j.Logger;

import com.kevfung.WeatherServiceApplication;

public class OpenWatherApiUtils {

	private static final Logger LOG = Logger.getLogger(WeatherServiceApplication.class);
		
	public static final String BASE_URL = "http://api.openweathermap.org/data/2.5";
	public static final String WEATHER_RESOURCE = "/weather";
	
	private static String openWeatherApiKey; 
	
	public static String getOpenWeatherApiKey() {
		return openWeatherApiKey;
	}
	
	/**
	 * Get Open Weather API key from configuration properties file and load
	 * it into the private local static variable
	 */
	public static void loadOpenWeatherApiKey(String fileName) {
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

			Optional<String> firstLine = stream.findFirst();
			if (firstLine.isPresent()) {
				final String keyValue = firstLine.get().toString();
				final int indexOfEqual = keyValue.indexOf("=");
				if (indexOfEqual >= 0) {
					openWeatherApiKey = keyValue.substring(indexOfEqual+1);
				} 
				else {
					LOG.warn("Could not find Open Weather API key of format \"key=value\" from " + fileName);
				}
			}
			else {
				LOG.warn("Did not find any configuration string in file " + fileName);
			}	
		} catch (IOException e) {
			LOG.error("Could not read from " + fileName, e);
		}		
	}
}
