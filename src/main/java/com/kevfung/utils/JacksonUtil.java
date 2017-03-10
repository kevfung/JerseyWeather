package com.kevfung.utils;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevfung.jsonclass.CurrentWeather;

/**
 * Set of utilities that make it easier to use Jackson for
 * JSON-object translation
 * 
 * @author Kevin
 *
 */
public class JacksonUtil {
	
	private static final Logger LOG = Logger.getLogger(JacksonUtil.class);
	
	/**
	 * Given a JSON string, we map it an POJO defined of class T
	 * 
	 * @param jsonStr JSON string
	 * @param clazz Class to map string to
	 * @return object of class T
	 */
	public static <T> T jsonToObj(String jsonStr, Class<T> clazz) {
		T classObj = null;
		ObjectMapper objectMapper = JacksonObjectMapper.INSTANCE.get();
				
		try {
			classObj = objectMapper.readValue(jsonStr, clazz);
			LOG.debug(classObj.toString());
		} catch (IOException e) {
			LOG.warn("Unable to parse JSON to " + CurrentWeather.class, e);
		}	
		
		return classObj;
	}
	
}
