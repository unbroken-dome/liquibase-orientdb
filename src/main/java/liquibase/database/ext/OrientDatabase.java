package liquibase.database.ext;

import liquibase.database.AbstractJdbcDatabase;
import liquibase.database.DatabaseConnection;
import liquibase.exception.DatabaseException;

public class OrientDatabase extends AbstractJdbcDatabase {

	public OrientDatabase() {
		setCurrentDateTimeFunction("sysdate()");
	}


	@Override
	public String getDefaultDatabaseProductName() {
		return OrientDatabaseConstants.PRODUCT_NAME;
	}


	@Override
	public String getShortName() {
		return OrientDatabaseConstants.SHORT_NAME;
	}


	@Override
	public Integer getDefaultPort() {
		return 2424;
	}


	@Override
	public boolean requiresUsername() {
		return true;
	}


	@Override
	public boolean requiresPassword() {
		return true;
	}


	@Override
	public boolean isCorrectDatabaseImplementation(DatabaseConnection conn) throws DatabaseException {
		return OrientDatabaseConstants.PRODUCT_NAME.equals(conn.getDatabaseProductName());
	}


	@Override
	public String getDefaultDriver(String url) {
		if (url.startsWith("jdbc:orient:")) {
			return "com.orientechnologies.orient.jdbc.OrientJdbcDriver";
		}
		return null;
	}
	
	
	@Override
	public boolean supportsDDLInTransaction() {
		return false;
	}


	@Override
	public boolean supportsInitiallyDeferrableColumns() {
		return false;
	}


	@Override
	public boolean supportsTablespaces() {
		return false;
	}


	@Override
	public boolean supportsCatalogs() {
		return false;
	}


	@Override
	public boolean supportsSchemas() {
		return false;
	}

	@Override
	public int getPriority() {
		return PRIORITY_DEFAULT;
	}
}
