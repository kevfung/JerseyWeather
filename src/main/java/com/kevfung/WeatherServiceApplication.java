package com.kevfung;

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
	
	public static String openWeatherApiKey;
	
	public WeatherServiceApplication() {
		// Add our packages as resources for this application
		packages("com.kevfung");
	}	

}
