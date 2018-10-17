/**
 * 
 */
package cz.fi.muni.pa165.soccermanager.main;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Class serves for spring to find DAO classes it will manage.
 * @author Michal Randak
 *
 */
@Configuration
@ComponentScan(basePackages = "cz.fi.muni.pa165.soccermanager.dao")
public class DAOBeansConfig {

}
