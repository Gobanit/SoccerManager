package cz.fi.muni.pa165.soccermanager.main;

import java.sql.SQLException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

/**
 * Class serves to config beans neccessary for using an in-memory database 
 * 
 * @author Michal Randak
 *
 */
@Configuration
public class PersistenceBeansConfig {
    private static final Logger LOG = LoggerFactory.getLogger(PersistenceBeansConfig.class);

    /**
     * Creates an instance of embedded H2 database using Spring-jdbc.
     * @return instance of embedded database as DataSource
     * @throws SQLException
     * 
     */
	@Bean
	public DataSource dataSource() throws SQLException{
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.H2).build();
		LOG.debug("Created Embedded DB");
		LOG.debug("URL: "+db.getConnection().getMetaData().getURL());
		LOG.debug("Type: "+db.getConnection().getMetaData().getDatabaseProductName());
		LOG.debug("Username: "+db.getConnection().getMetaData().getUserName());
		return db;		
	}
	
	/**
	 * Creates an instance of EntityManagerFactory based on 'default' persistence unit
	 * @return create entity manager factory instance
	 * @throws SQLException
	 */
	@Bean(destroyMethod = "close")
	public EntityManagerFactory entityManagerFactory() throws SQLException{
		return Persistence.createEntityManagerFactory("default");
	}
}
