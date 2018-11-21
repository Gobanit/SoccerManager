/**
 * 
 */
package cz.fi.muni.pa165.soccermanager.service;

import org.modelmapper.ModelMapper;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import cz.fi.muni.pa165.soccermanager.api.dto.UserCreateDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.UserDTO;
import cz.fi.muni.pa165.soccermanager.data.User;

/**
 * @author Michal Randak
 *
 */
public class BeanMappingTest {

	private ModelMapper mapper;
	private BeanMapping beanMapping;
	
	@BeforeTest
	public void init() {
		mapper = new ModelMapper();
		
		beanMapping = new BeanMappingImpl(mapper);
	}
	
	
	@Test
	public void testUserCreateMapping() {
		UserCreateDTO dto = new UserCreateDTO();
		dto.setUsername("someuser");
		dto.setRawPassword("pass");
		dto.setAdmin(false);
		
		
		User user = beanMapping.mapTo(dto, User.class);
		
		Assert.assertEquals(user.getUserName(), dto.getUsername());
		Assert.assertEquals(user.isAdmin(), dto.isAdmin());
	}
	
	@Test
	public void testUserMapping() {
		User user = new User();
		user.setId(10l);
		user.setUserName("SomeSuperUser");
		user.setPasswordHash("A12B34");
		user.setAdmin(false);
		user.setTeam(null);		
		
		UserDTO userDTO = beanMapping.mapTo(user, UserDTO.class);
		
		Assert.assertEquals(userDTO.getId(), user.getId());
		Assert.assertEquals(userDTO.getUsername(), user.getUserName());
		Assert.assertEquals(userDTO.getPasswordHash(), user.getPasswordHash());
		Assert.assertEquals(userDTO.isAdmin(), user.isAdmin());	
	}
}
