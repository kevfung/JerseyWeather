package com.kevfung;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

import javax.ws.rs.ApplicationPath;

import org.apache.log4j.Logger;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * This is the {@link ResourceConfig} for this weather service application
 * 
 * @author Kevin
 *
 */
@ApplicationPath("")
public class WeatherServiceApplication extends ResourceConfig {
	
	static final Logger LOG = Logger.getLogger(WeatherServiceApplication.class);
	static final String PROPERTIES_FILE = "gradle.properties";
	
	public static String openWeatherApiKey;
	
	public WeatherServiceApplication() {
		// Add our packages as resources for this application
		packages("com.kevfung");
		
		loadOpenWeatherApiKey(PROPERTIES_FILE);
	}
	
	/**
	 * Get Open Weather API key from configuration properties file and load
	 * it into the local static variable
	 */
	void loadOpenWeatherApiKey(String fileName) {
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

			Optional<String> firstLine = stream.findFirst();
			if (firstLine.isPresent()) {
				final String keyValue = firstLine.get().toString();
				final int indexOfEqual = keyValue.indexOf("=");
				if (indexOfEqual >= 0) {
					openWeatherApiKey = keyValue.substring(indexOfEqual+1);
				} 
				else {
					LOG.warn("Could not retrieve Open Weather API key from " + fileName);
				}
			}
			else {
				LOG.warn("Could not retrieve Open Weather API key from " + fileName);
			}	
		} catch (IOException e) {
			LOG.error("Could not read from " + fileName, e);
		}		
	}
}
