package cz.fi.muni.pa165.soccermanager.main;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;


@Configuration
public class InMemoryDatabaseSpring {

	@Bean
	public DataSource db() throws SQLException{
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.H2).build();
		System.out.println("Created Embedded DB");
		System.out.println("URL: "+db.getConnection().getMetaData().getURL());
		System.out.println("Type: "+db.getConnection().getMetaData().getDatabaseProductName());
		System.out.println("Username: "+db.getConnection().getMetaData().getUserName());
		return db;		
	}
}
