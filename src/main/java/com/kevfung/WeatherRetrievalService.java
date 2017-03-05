package com.kevfung;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevfung.utils.OpenWeatherApiUtil;
import com.kevfung.utils.jsonclasses.CurrentWeather;

/**
 * Service used to communicate with Open Weather APi to retrieve 
 * weather information
 * 
 * @author Kevin
 *
 */
@Path("/openweather")
public class WeatherRetrievalService {

	private static final Logger LOG = Logger.getLogger(WeatherRetrievalService.class);
	
	@GET
	@Path("/current")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getCurrentWeather(@Context UriInfo queryParams) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(OpenWeatherApiUtil.BASE_URL)
				.path(OpenWeatherApiUtil.WEATHER_RESOURCE)
				.queryParam(OpenWeatherApiUtil.CITY_QUERY_PARAM, "Vancouver")
				.queryParam(OpenWeatherApiUtil.UNITS_QUERY_PARAM, OpenWeatherApiUtil.METRIC_UNITS)
				.queryParam(OpenWeatherApiUtil.API_KEY_QUERY_PARAM, OpenWeatherApiUtil.getOpenWeatherApiKey());

		LOG.debug("Target URL: " + target.getUri().toString());
		
		Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);		
		String jsonResponse = invocationBuilder.get(String.class);		
		LOG.debug(jsonResponse);
		
		ObjectMapper objectMapper = new ObjectMapper();
		CurrentWeather currentWeather = null;
		try {
			currentWeather = objectMapper.readValue(jsonResponse, CurrentWeather.class);
			LOG.debug(currentWeather.toString());
		} catch (IOException e) {
			LOG.warn("Unable to parse JSON to " + CurrentWeather.class, e);
		}		
		
		return Response.status(200)
				.entity(currentWeather != null ? currentWeather.toString().replace("\n", "<br>") : "Error: Unable to parse JSON response")
				.build();
	}
}
