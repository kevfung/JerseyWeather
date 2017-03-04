package com.kevfung;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

import javax.ws.rs.ApplicationPath;

import org.apache.log4j.Logger;
import org.glassfish.jersey.server.ResourceConfig;

import com.kevfung.utils.OpenWatherApiUtils;

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
		
		try {
			OpenWatherApiUtils.loadOpenWeatherApiKey(PROPERTIES_FILE);
		}
		catch(IllegalStateException e) {
			LOG.error(e.getMessage(), e);
		}
	}	

}
