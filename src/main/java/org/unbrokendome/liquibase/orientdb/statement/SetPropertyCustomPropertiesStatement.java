package org.unbrokendome.liquibase.orientdb.statement;

import java.util.Map;

import liquibase.statement.AbstractSqlStatement;

public class SetPropertyCustomPropertiesStatement extends AbstractSqlStatement {

	private String className;
	private String propertyName;
	private Map<String, Object> customProperties;


	public String getClassName() {
		return className;
	}


	public SetPropertyCustomPropertiesStatement setClassName(String className) {
		this.className = className;
		return this;
	}


	public String getPropertyName() {
		return propertyName;
	}


	public SetPropertyCustomPropertiesStatement setPropertyName(String propertyName) {
		this.propertyName = propertyName;
		return this;
	}


	public Map<String, Object> getCustomProperties() {
		return customProperties;
	}


	public SetPropertyCustomPropertiesStatement setCustomProperties(Map<String, Object> customProperties) {
		this.customProperties = customProperties;
		return this;
	}
}
