package com.kevfung;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Service used to communicate with Open Weather APi to retrieve 
 * weather information
 * 
 * @author Kevin
 *
 */
@Path("/openweather")
public class WeatherRetrievalService {

	@GET
	@Path("/current")
	public Response getCurrentWeather(@Context UriInfo queryParams) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("");
		
		return null;
	}
}
