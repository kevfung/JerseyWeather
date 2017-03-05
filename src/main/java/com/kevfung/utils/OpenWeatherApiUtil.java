package com.kevfung.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.log4j.Logger;

import com.kevfung.WeatherServiceApplication;

public class OpenWeatherApiUtil {

	private static final Logger LOG = Logger.getLogger(OpenWeatherApiUtil.class);
	private static final String PROPERTIES_FILE = "gradle.properties";
		
	public static final String BASE_URL = "http://api.openweathermap.org/data/2.5";
	public static final String WEATHER_RESOURCE = "/weather";

	public static final String API_KEY_QUERY_PARAM = "APPID";
	public static final String CITY_QUERY_PARAM = "q";
	public static final String UNITS_QUERY_PARAM = "units";	
	public static final String METRIC_UNITS = "metric";	// used by UNITS_QUERY_PARAM
	
	private static String openWeatherApiKey; 
	
	public static String getOpenWeatherApiKey() {
		if (openWeatherApiKey == null || openWeatherApiKey.length() == 0) {
			try {
				loadOpenWeatherApiKey(PROPERTIES_FILE);
			}
			catch (IllegalStateException e) {
				LOG.error(e.getMessage(), e);
			}
		}
		
		return openWeatherApiKey;
	}
	
	/**
	 * Get Open Weather API key from configuration properties file and load
	 * it into the private local static variable
	 * 
	 * @param fileName the filename and path of where the API key is stored
	 * @throws IllegalStateException
	 */
	public static void loadOpenWeatherApiKey(String fileName) throws IllegalStateException {
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

			Optional<String> firstLine = stream.findFirst();
			if (firstLine.isPresent()) {
				final String keyValue = firstLine.get().toString();
				final int indexOfEqual = keyValue.indexOf("=");
				if (indexOfEqual >= 0) {
					openWeatherApiKey = keyValue.substring(indexOfEqual+1);
				} 
				else {					
					throw new IllegalStateException("Could not find Open Weather API key of format \"key=value\" from " + fileName);
				}
			}
			else {
				throw new IllegalStateException("Did not find any configuration string in file " + fileName);
			}				
		} catch (IOException e) {
			throw new IllegalStateException("Could not read from " + fileName, e);
		}	
	}
}
