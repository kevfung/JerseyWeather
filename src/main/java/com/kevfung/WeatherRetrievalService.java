package com.kevfung;

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

import com.kevfung.utils.OpenWatherApiUtils;

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
	public Response getCurrentWeather(@Context UriInfo queryParams) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(OpenWatherApiUtils.BASE_URL)
				.path(OpenWatherApiUtils.WEATHER_RESOURCE)
				.queryParam(OpenWatherApiUtils.CITY_QUERY_PARAM, "Vancouver")
				.queryParam(OpenWatherApiUtils.API_KEY_QUERY_PARAM, OpenWatherApiUtils.getOpenWeatherApiKey());

		LOG.debug("Target URL: " + target.getUri().toString());
		
		Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
		
		String jsonResponse = invocationBuilder.get(String.class);
		
		return Response.status(200).entity(jsonResponse).build();
	}
}
