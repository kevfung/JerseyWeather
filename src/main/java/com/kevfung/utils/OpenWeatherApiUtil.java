package com.kevfung.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

public class OpenWeatherApiUtil {

	private static final Logger LOG = Logger.getLogger(OpenWeatherApiUtil.class);
	
	// Properties file
	private static final String PROPERTIES_FILE = "gradle.properties";
	private static final String API_KEY_PROPERTY = "API_KEY";
		
	// URIs
	public static final String BASE_URL = "http://api.openweathermap.org/data/2.5";
	public static final String WEATHER_RESOURCE = "/weather";

	// Query parameters & values
	public static final String API_KEY_QUERY_PARAM = "APPID"; // Usually not safe to put API key in query param, but Open Weather API only allows this method
	
	public static final String CITY_QUERY_PARAM = "q";
	private static final String CITY_VANCOUVER = "Vancouver";
	
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
		FileInputStream propertiesFile = null;
		
		try {
			propertiesFile = new FileInputStream(fileName);
			Properties properties = new Properties();
			properties.load(propertiesFile);
			openWeatherApiKey = properties.getProperty(API_KEY_PROPERTY);
			
			if (openWeatherApiKey == null) {
				throw new IllegalStateException("Could not find Open Weather API key of format \"API_KEY=<value>\" from " + fileName);
			}			
		} catch (FileNotFoundException e) {
			throw new IllegalStateException("File with name " + fileName + " does not exist.", e);
		} catch (IOException e) {
			throw new IllegalStateException("Could not read from " + fileName, e);
		}
		finally {
			if (propertiesFile != null) {
				try {
					propertiesFile.close();
				} catch (IOException e) {
					LOG.warn("Could not close input stream for file " + fileName, e);
				}				
			}
		}		
	}
	
	/**
	 * Gets the current weather from Open Weather API and
	 * retuns the JSON response as a string
	 * 
	 * @return JSON response for current weather
	 */
	public static String getOpenWeatherApiCurrentWeather() {
		String jsonStr = null;
		
		Client client = ClientBuilder.newClient();		
		WebTarget target = client.target(OpenWeatherApiUtil.BASE_URL)
				.path(OpenWeatherApiUtil.WEATHER_RESOURCE)
				.queryParam(OpenWeatherApiUtil.CITY_QUERY_PARAM, CITY_VANCOUVER)
				.queryParam(OpenWeatherApiUtil.UNITS_QUERY_PARAM, OpenWeatherApiUtil.METRIC_UNITS)
				.queryParam(OpenWeatherApiUtil.API_KEY_QUERY_PARAM, OpenWeatherApiUtil.getOpenWeatherApiKey());

		LOG.debug("Target URL: " + target.getUri().toString());
		
		Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);		
		
		Response response = invocationBuilder.get(Response.class);		
		jsonStr = response.readEntity(String.class);
		
		// Check response from Open Weather API and log the response appropriately
		if (response.getStatus() == 200) {
			LOG.debug(jsonStr);
		}
		else {
			LOG.error(ErrorMessageUtils.ERROR_OPEN_WEATHER_API_RESPONSE_NON_200_STATUS
					+ "\n" + response.readEntity(String.class));
		}
			
		return jsonStr;
	}
}
