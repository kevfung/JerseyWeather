package com.kevfung;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.kevfung.utils.OpenWeatherApiUtil;

public class Test_OpenWeatherApiUtils {

	private static final String TEST_FILE_NAME = "test.txt";
	private static final String TEST_PROP_KEY = "ApiKey";
	private static final String TEST_PROP_VALUE = "1234567890";	
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Before
	public void setup() {
		// Do log4j configuration
		BasicConfigurator.configure();
	}
	
	/**
	 * Create test file with key value pair
	 */
	public void createTestFileWithKey() {
		List<String> lines = Arrays.asList(TEST_PROP_KEY + "=" + TEST_PROP_VALUE);
		Path file = Paths.get(TEST_FILE_NAME);
		try {
			Files.write(file, lines, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create test file without proper key value pair format
	 */
	public void createTestFileWithoutProperKeyFormat() {
		List<String> lines = Arrays.asList(TEST_PROP_KEY + TEST_PROP_VALUE);
		Path file = Paths.get(TEST_FILE_NAME);
		try {
			Files.write(file, lines);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create test file without any information inside
	 */
	public void createTestFileWithoutKey() {
		List<String> lines = new ArrayList<>(0);
		Path file = Paths.get(TEST_FILE_NAME);
		try {
			Files.write(file, lines);
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
		createTestFileWithKey();
		OpenWeatherApiUtil.loadOpenWeatherApiKey(TEST_FILE_NAME);
		assertTrue("Expecting " + TEST_PROP_VALUE + " to be loaded from " + TEST_FILE_NAME
				,TEST_PROP_VALUE.equals(OpenWeatherApiUtil.getOpenWeatherApiKey()));
	}
	
	@Test(expected=IllegalStateException.class)
	public void test_loadOpenWeatherApiKey_failure_file_not_exist() {
		deleteTestFile();
		OpenWeatherApiUtil.loadOpenWeatherApiKey(TEST_FILE_NAME);
	}
	
	@Test
	public void test_loadOpenWeatherApiKey_failure_no_key_value_pair() {
		expectedException.expect(IllegalStateException.class);
		expectedException.expectMessage("Did not find any configuration string in file " + TEST_FILE_NAME);
		
		deleteTestFile();
		createTestFileWithoutKey();
		OpenWeatherApiUtil.loadOpenWeatherApiKey(TEST_FILE_NAME);
	}
	
	@Test
	public void test_loadOpenWeatherApiKey_failure_invalid_key_value_pair_format() {
		expectedException.expect(IllegalStateException.class);
		expectedException.expectMessage("Could not find Open Weather API key of format \"key=value\" from " + TEST_FILE_NAME);
		
		deleteTestFile();
		createTestFileWithoutProperKeyFormat();
		OpenWeatherApiUtil.loadOpenWeatherApiKey(TEST_FILE_NAME);
	}

}
