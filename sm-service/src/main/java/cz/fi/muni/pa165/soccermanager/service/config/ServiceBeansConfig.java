/**
 * 
 */
package cz.fi.muni.pa165.soccermanager.service.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import cz.fi.muni.pa165.soccermanager.dao.config.DAOBeansConfig;

/**
 * @author Michal Randak
 *
 */
@Configuration
@Import(DAOBeansConfig.class)
@ComponentScan(basePackages = { "cz.fi.muni.pa165.soccermanager.service", 
		"cz.fi.muni.pa165.soccermanager.service.facade" })
public class ServiceBeansConfig {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		
		return modelMapper;
	}
}
