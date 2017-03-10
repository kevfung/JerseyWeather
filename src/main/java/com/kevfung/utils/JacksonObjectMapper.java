package com.kevfung.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Singleton instance of Jackson ObjectMapper since it is expensive
 * to create many instances of these.
 *  
 * @author Kevin
 *
 */
public enum JacksonObjectMapper {
	INSTANCE;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	public ObjectMapper get() { return objectMapper; }
}
