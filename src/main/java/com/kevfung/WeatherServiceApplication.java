package com.kevfung;

import javax.ws.rs.ApplicationPath;

import org.apache.log4j.Logger;
import org.glassfish.jersey.server.ResourceConfig;

import com.kevfung.utils.weather.OpenWeatherApiUtil;

/**
 * This is the {@link ResourceConfig} for this weather service application
 * 
 * @author Kevin
 *
 */
@ApplicationPath("/")
public class WeatherServiceApplication extends ResourceConfig {
	
	private static final Logger LOG = Logger.getLogger(WeatherServiceApplication.class);
	
	public static final String WEATHER_UTIL_PROPERTY = "WeatherUtil";
	
	public WeatherServiceApplication() {
		// Add our packages as resources for this application
		packages("com.kevfung");
		property(WEATHER_UTIL_PROPERTY, new OpenWeatherApiUtil());
	}	

}
