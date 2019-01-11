/**
 * 
 */
package cz.fi.muni.pa165.soccermanager.rest.controllers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import cz.fi.muni.pa165.soccermanager.api.dto.MatchDTO;


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
	public void serializingMatchDTO() throws JsonProcessingException {
	    LocalDateTime date = LocalDateTime.of(2014, 12, 20, 2, 30);
	    Instant instant = date.toInstant(ZoneOffset.UTC);
	    
	    MatchDTO match = new MatchDTO();
		match.setDate(instant);
		match.setId(10l);
		match.setHomeTeamGoals(100);
	 
	    ObjectMapper om = createObjectMapper();
	 
	    String result = om.writeValueAsString(match);
	    
        System.out.println("Result: "+result);
	    Assert.assertTrue(result.contains("2014-12-20T02:30"));
	}
	
	
	@Test
	public void whenSerializingJava8Date_thenCorrect()
	  throws JsonProcessingException {
	    LocalDateTime date = LocalDateTime.of(2014, 12, 20, 2, 30);

	    Instant instant = date.toInstant(ZoneOffset.ofHours(-1));
	    
	    ObjectMapper om = createObjectMapper();
	    om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        om.registerModule(javaTimeModule);
		
	    //om.enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	    String result = om.writeValueAsString(instant);
        System.out.println("Result: "+result);
	    Assert.assertTrue(result.contains("2014-12-20T03:30"));
	}
}
