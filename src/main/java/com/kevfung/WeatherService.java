package com.kevfung;

import java.io.StringWriter;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.kevfung.jsonclass.CurrentWeather;
import com.kevfung.utils.JacksonUtil;
import com.kevfung.utils.OpenWeatherApiUtil;
import com.kevfung.utils.VelocityUtil;

/**
 * This is the weather service resource
 * 
 * @author Kevin
 *
 */
@Path("/weather")
public class WeatherService {
	
	private static final Logger LOG = Logger.getLogger(WeatherService.class);
	
	/**
	 * This method is for testing our weather service.
	 * 
	 * @param msg String from the given path
	 * @param queryParams Any query parameters passed in
	 * @return outputs a message containing the URI parameter and any query parameters if they exist
	 */
	@GET
	@Path("/{param}")
	public Response getTestMsg(@PathParam("param") String msg, @Context UriInfo queryParams) {
		StringBuilder output = new StringBuilder("Your URI parameter: " + msg);	
		
		MultivaluedMap<String, String> queryParam = queryParams.getQueryParameters();
		Set<Entry<String, List<String>>> queryKeyValuePairs = queryParam.entrySet();
		
		for(Entry<String, List<String>> keyValuePair : queryKeyValuePairs) {
			output.append("<br>").append(keyValuePair.getKey()).append(" - ").append(keyValuePair.getValue());
		}		
		
		return Response.status(200).entity(output.toString()).build();
	}
	
	@GET
	@Path("/current")
	public Response getCurrentWeatherInfo() {		
		String json = OpenWeatherApiUtil.getOpenWeatherApiCurrentWeather();
		CurrentWeather currentWeather = JacksonUtil.jsonToObj(json, CurrentWeather.class);

		Velocity.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		Velocity.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		Velocity.init();

		VelocityContext context = new VelocityContext();		
		context.put("weather", currentWeather);
		StringWriter writer = new StringWriter();
		
		Velocity.mergeTemplate(VelocityUtil.SHOW_WEATHER_TEMPLATE, "UTF-8", context, writer);
		
		return Response.status(200).entity(writer.toString()).build();	
	}
}
