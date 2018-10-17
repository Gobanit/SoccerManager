/**
 * 
 */
package cz.fi.muni.pa165.soccermanager.main;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michal Randak
 *
 */

@Configuration
@ComponentScan(basePackages = "cz.fi.muni.pa165.soccermanager.dao")
public class DAOBeansConfig {

}
