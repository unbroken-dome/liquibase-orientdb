package org.unbrokendome.liquibase.orientdb.sql;


public class AlterDatabaseSql extends AbstractOrientSql {

	private final String attributeName;
	private final Object attributeValue;


	public AlterDatabaseSql(String attributeName, Object attributeValue) {
		this.attributeName = attributeName;
		this.attributeValue = attributeValue;
	}


	@Override
	public String toSql() {
		return "ALTER DATABASE " + attributeName + " " + attributeValue;
	}
}
