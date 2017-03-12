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
import com.kevfung.utils.ErrorMessageUtils;

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
		String jsonResponse = OpenWeatherApiUtil.getOpenWeatherApiCurrentWeather();
		
		CurrentWeather currentWeather = JacksonUtil.jsonToObj(jsonResponse, CurrentWeather.class);
		
		// If we didn't' get the current weather from the JSON, then return error message to user
		if (currentWeather == null) {
			return Response.status(500)
					.entity(ErrorMessageUtils.ERROR_OPEN_WEATHER_API_GET_CURRENT_WEATHER)
					.build();
		}
		
		return Response.status(200)
				.entity(currentWeather.toString().replace("\n", "<br>"))
				.build();
	}
}
