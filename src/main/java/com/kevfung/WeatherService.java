package com.kevfung;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * This is the weather service resource
 * 
 * @author Kevin
 *
 */
@Path("/weather")
public class WeatherService {
	
	/**
	 * This method is for testing our weather service.
	 * 
	 * @param msg String from the given path
	 * @param info Any query parameters passed in
	 * @return outputs a message containing the URI parameter and any query parameters if they exist
	 */
	@GET
	@Path("/{param}")
	public Response getTestMsg(@PathParam("param") String msg, @Context UriInfo info) {
		StringBuffer output = new StringBuffer("Your URI parameter: " + msg);	
		
		MultivaluedMap<String, String> queryParam = info.getQueryParameters();
		Set<Entry<String, List<String>>> queryKeyValuePairs = queryParam.entrySet();
		
		for(Entry<String, List<String>> keyValuePair : queryKeyValuePairs) {
			output.append("<br>").append(keyValuePair.getKey()).append(" - ").append(keyValuePair.getValue());
		}		

		return Response.status(200).entity(output.toString()).build();
	}
}
