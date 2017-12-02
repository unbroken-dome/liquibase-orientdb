package org.unbrokendome.liquibase.orientdb;

import com.orientechnologies.orient.core.config.OGlobalConfiguration;
import com.orientechnologies.orient.core.db.ODatabase;
import com.orientechnologies.orient.core.db.ODatabasePool;
import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.db.ODatabaseType;
import com.orientechnologies.orient.core.db.OrientDB;
import com.orientechnologies.orient.core.db.OrientDBConfig;
import com.orientechnologies.orient.jdbc.OrientDataSource;
import com.orientechnologies.orient.jdbc.OrientJdbcConnection;
import org.junit.rules.ExternalResource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Properties;
import java.util.UUID;


public class InMemoryDatabase extends ExternalResource {

	private final String databaseName;
	private final OrientDB orientDB;
	private ODatabaseSession database;
	private final ODatabasePool pool;


	public InMemoryDatabase(String databaseName) {
		this.databaseName = databaseName;

		OrientDBConfig config = OrientDBConfig.builder()
				.addAttribute(ODatabase.ATTRIBUTES.TYPE, "graph")
				.addConfig(OGlobalConfiguration.CREATE_DEFAULT_USERS, true)
				.build();
		orientDB = new OrientDB("embedded:.", config);

		pool = new ODatabasePool(orientDB, this.databaseName, "admin", "admin");
	}


	@Override
	protected void before() {
	    orientDB.create(databaseName, ODatabaseType.MEMORY);
	    database = orientDB.open(databaseName, "admin", "admin");
	}


	@Override
	protected void after() {
		database.activateOnCurrentThread();
		database.close();

		orientDB.drop(databaseName);
	}


	public String getDatabaseURL() {
		return database.getURL();
	}


	public String getJdbcURL() {
		return "jdbc:orient:" + getDatabaseURL();
	}


	public Connection getJdbcConnection() {
		return new OrientJdbcConnection(pool.acquire(), orientDB, new Properties());
	}


	public ODatabaseSession getDatabase() {
		return database;
	}
}
