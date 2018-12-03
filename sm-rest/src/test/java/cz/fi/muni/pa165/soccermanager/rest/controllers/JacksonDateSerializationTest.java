/**
 * 
 */
package cz.fi.muni.pa165.soccermanager.rest.controllers;

import java.time.LocalDateTime;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


/**
 * @author Michal Randak
 *
 */

public class JacksonDateSerializationTest {

	@Test
	public void whenSerializingJava8Date_thenCorrect()
	  throws JsonProcessingException {
	    LocalDateTime date = LocalDateTime.of(2014, 12, 20, 2, 30);
	 
	    ObjectMapper objectMapper = new ObjectMapper();
	    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        objectMapper.registerModule(javaTimeModule);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
	 
	    String result = objectMapper.writeValueAsString(date);
        System.out.println("Result: "+result);
	    Assert.assertTrue(result.contains("2014-12-20T02:30"));
	}
}
