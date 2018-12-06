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

import cz.fi.muni.pa165.soccermanager.data.Match;


/**
 * @author Michal Randak
 *
 */

public class JacksonDateSerializationTest {

	private ObjectMapper createObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
	    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        objectMapper.registerModule(javaTimeModule);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        
        return objectMapper;
	}
	
	@Test
	public void serializingMatch() throws JsonProcessingException {
	    LocalDateTime date = LocalDateTime.of(2014, 12, 20, 2, 30);

		Match match = new Match();
		match.setDate(date);
		match.setId(10l);
	 
	    ObjectMapper om = createObjectMapper();
	 
	    String result = om.writeValueAsString(match);
	    
        System.out.println("Result: "+result);
	    Assert.assertTrue(result.contains("2014-12-20T02:30"));
	}
	
	
	@Test
	public void whenSerializingJava8Date_thenCorrect()
	  throws JsonProcessingException {
	    LocalDateTime date = LocalDateTime.of(2014, 12, 20, 2, 30);
	 
	    ObjectMapper om = createObjectMapper();
	    
	    String result = om.writeValueAsString(date);
        System.out.println("Result: "+result);
	    Assert.assertTrue(result.contains("2014-12-20T02:30"));
	}
}
