package com.kevfung;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.kevfung.jsonclass.CurrentWeather;
import com.kevfung.utils.JacksonUtil;
import com.kevfung.utils.OpenWeatherApiUtil;

@RunWith(PowerMockRunner.class)
public class Test_WeatherRetrievalService extends JerseyTest {	
	
	// Fake open weather API response JSON
	private static String OPEN_WEATHER_API_JSON_RESPONSE = "{\r\ncoord: {\r\nlon: -123.12,\r\nlat: 49.25\r\n},\r\nweather: [\r\n{\r\nid: 500,\r\nmain: \"Rain\",\r\ndescription: \"light rain\",\r\nicon: \"10n\"\r\n},\r\n{\r\nid: 701,\r\nmain: \"Mist\",\r\ndescription: \"mist\",\r\nicon: \"50n\"\r\n}\r\n],\r\nbase: \"stations\",\r\nmain: {\r\ntemp: 2.63,\r\npressure: 1008,\r\nhumidity: 100,\r\ntemp_min: -3,\r\ntemp_max: 5\r\n},\r\nvisibility: 16093,\r\nwind: {\r\nspeed: 3.6,\r\ndeg: 80\r\n},\r\nclouds: {\r\nall: 90\r\n},\r\ndt: 1489132800,\r\nsys: {\r\ntype: 1,\r\nid: 3359,\r\nmessage: 0.007,\r\ncountry: \"CA\",\r\nsunrise: 1489156489,\r\nsunset: 1489198262\r\n},\r\nid: 6173331,\r\nname: \"Vancouver\",\r\ncod: 200\r\n}";
	
	@Override
    protected Application configure() {
        return new ResourceConfig(WeatherRetrievalService.class);
    }	
	
	@Test
	@PrepareForTest({JacksonUtil.class, OpenWeatherApiUtil.class})
	public void test_getCurrentWeather_success() {
		CurrentWeather fakeCurrentWeather = mock(CurrentWeather.class);
		
		PowerMockito.mockStatic(OpenWeatherApiUtil.class);
		PowerMockito.when(OpenWeatherApiUtil.getOpenWeatherApiCurrentWeather()).thenReturn(OPEN_WEATHER_API_JSON_RESPONSE);
		
		PowerMockito.mockStatic(JacksonUtil.class);
		PowerMockito.when(JacksonUtil.jsonToObj(anyString(), anyObject())).thenReturn(fakeCurrentWeather);		
		
		Response response = target("/openweather/current").request().get(Response.class);
		
		assertTrue("Did not get HTTP status 200 as expected", response.getStatus() == 200);
				
		PowerMockito.verifyStatic(times(1));
		JacksonUtil.jsonToObj(OPEN_WEATHER_API_JSON_RESPONSE, CurrentWeather.class);
	}
	
	@Test
	@PrepareForTest({JacksonUtil.class, OpenWeatherApiUtil.class})
	public void test_getCurrentWeather_failure_JSON_null() {	
		PowerMockito.mockStatic(OpenWeatherApiUtil.class);
		PowerMockito.when(OpenWeatherApiUtil.getOpenWeatherApiCurrentWeather()).thenReturn(OPEN_WEATHER_API_JSON_RESPONSE);
		
		PowerMockito.mockStatic(JacksonUtil.class);
		PowerMockito.when(JacksonUtil.jsonToObj(anyString(), anyObject())).thenReturn(null);
		
		Response response = target("/openweather/current").request().get(Response.class);
		
		assertTrue("Did not get HTTP status 500 as expected", response.getStatus() == 500);
				
		PowerMockito.verifyStatic(times(1));
		JacksonUtil.jsonToObj(OPEN_WEATHER_API_JSON_RESPONSE, CurrentWeather.class);
	}	
}
