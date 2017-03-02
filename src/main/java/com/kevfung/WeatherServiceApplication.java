package com.kevfung;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * This is the {@link ResourceConfig} for this weather service application
 * 
 * @author Kevin
 *
 */
@ApplicationPath("")
public class WeatherServiceApplication extends ResourceConfig {    
	
	public WeatherServiceApplication() {
		// Add our packages as resources for this application
		packages("com.kevfung");
	}
}
