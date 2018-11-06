/**
 * 
 */
package cz.fi.muni.pa165.soccermanager.service.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import cz.fi.muni.pa165.soccermanager.main.DAOBeansConfig;

/**
 * @author Michal Randak
 *
 */
@Configuration
@Import(DAOBeansConfig.class)
@ComponentScan(basePackageClasses={})
public class ServiceBeansConfig {

}
