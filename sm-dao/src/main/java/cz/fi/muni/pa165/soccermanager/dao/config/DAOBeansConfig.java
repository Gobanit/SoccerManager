/**
 * 
 */
package cz.fi.muni.pa165.soccermanager.dao.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Class serves for spring to find DAO classes it will manage.
 * @author Michal Randak
 *
 */
@Configuration
@EnableJpaRepositories
@ComponentScan(basePackages = "cz.fi.muni.pa165.soccermanager.dao")
@Import(PersistenceBeansConfig.class)
public class DAOBeansConfig {

}
