package com.kevfung;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.kevfung.utils.OpenWatherApiUtils;

public class Test_OpenWeatherApiUtils {

	private static final String TEST_FILE_NAME = "test.txt";
	private static final String TEST_PROP_KEY = "ApiKey";
	private static final String TEST_PROP_VALUE = "1234567890";	
	
	@Before
	public void setup() {
		// Do log4j configuration
		BasicConfigurator.configure();
	}
	
	/**
	 * Create test file with key value pair
	 */
	public void createTestFile() {
		List<String> lines = Arrays.asList(TEST_PROP_KEY + "=" + TEST_PROP_VALUE);
		Path file = Paths.get(TEST_FILE_NAME);
		try {
			Files.write(file, lines, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Delete test file
	 */
	@After
	public void deleteTestFile() {
	    try {
			Files.delete(Paths.get(TEST_FILE_NAME));
		} catch (IOException e) {
		}		
	}
	
	@Test
	public void test_loadOpenWeatherApiKey_success() {
		createTestFile();
		OpenWatherApiUtils.loadOpenWeatherApiKey(TEST_FILE_NAME);
		assertTrue("Expecting " + TEST_PROP_VALUE + " to be loaded from " + TEST_FILE_NAME
				,TEST_PROP_VALUE.equals(OpenWatherApiUtils.getOpenWeatherApiKey()));
	}	

}
