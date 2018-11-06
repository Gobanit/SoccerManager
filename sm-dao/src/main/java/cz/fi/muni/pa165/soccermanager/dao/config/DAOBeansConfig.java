/**
 * 
 */
package cz.fi.muni.pa165.soccermanager.dao.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Class serves for spring to find DAO classes it will manage.
 * @author Michal Randak
 *
 */
@Configuration
@ComponentScan(basePackages = "cz.fi.muni.pa165.soccermanager.dao")
@Import(PersistenceBeansConfig.class)
public class DAOBeansConfig {

}
