package com.kevfung;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;

import com.kevfung.jsonclass.CurrentWeather;
import com.kevfung.utils.JacksonUtil;
import com.kevfung.utils.OpenWeatherApiUtil;

/**
 * Service used to communicate with Open Weather API to retrieve 
 * weather information
 * 
 * @author Kevin
 *
 */
@Path("/openweather")
public class WeatherRetrievalService {

	private static final Logger LOG = Logger.getLogger(WeatherRetrievalService.class);

	// Which locale to get weather for
	private static final String LOCALE_VANCOUVER = "Vancouver";
	
	/**
	 * Gets the current weather by calling the Open Weather Service API
	 * and converts it to a {@link CurrentWeather} object. Then it 
	 * prints out the object.
	 * 
	 * @return
	 */
	@GET
	@Path("/current")
	public Response getCurrentWeather() {
		String jsonResponse = getOpenWeatherApiCurrentWeather();
		
		CurrentWeather currentWeather = JacksonUtil.jsonToObj(jsonResponse, CurrentWeather.class);
		
		return Response.status(200)
				.entity(currentWeather != null ? currentWeather.toString().replace("\n", "<br>") : "Error: Unable to parse JSON response")
				.build();
	}

	/**
	 * Gets the current weather from Open Weather API and
	 * retuns the JSON response as a string
	 * 
	 * @return JSON response for current weather
	 */
	public String getOpenWeatherApiCurrentWeather() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(OpenWeatherApiUtil.BASE_URL)
				.path(OpenWeatherApiUtil.WEATHER_RESOURCE)
				.queryParam(OpenWeatherApiUtil.CITY_QUERY_PARAM, LOCALE_VANCOUVER)
				.queryParam(OpenWeatherApiUtil.UNITS_QUERY_PARAM, OpenWeatherApiUtil.METRIC_UNITS)
				.queryParam(OpenWeatherApiUtil.API_KEY_QUERY_PARAM, OpenWeatherApiUtil.getOpenWeatherApiKey());

		LOG.debug("Target URL: " + target.getUri().toString());
		
		Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);		
		String jsonStr = invocationBuilder.get(String.class);		
		LOG.debug(jsonStr);
		
		return jsonStr;
	}
}
