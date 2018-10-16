package cz.fi.muni.pa165.soccermanager.main;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;


@Configuration
public class InMemoryDatabaseSpring {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryDatabaseSpring.class);

	@Bean
	public DataSource db() throws SQLException{
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.H2).build();
		LOG.debug("Created Embedded DB");
		LOG.debug("URL: "+db.getConnection().getMetaData().getURL());
		LOG.debug("Type: "+db.getConnection().getMetaData().getDatabaseProductName());
		LOG.debug("Username: "+db.getConnection().getMetaData().getUserName());
		return db;		
	}
}
