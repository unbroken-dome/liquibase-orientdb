package org.unbrokendome.liquibase.orientdb.statement;

import java.util.Map;

import liquibase.statement.AbstractSqlStatement;

public class SetClassCustomPropertiesStatement extends AbstractSqlStatement {

	private String className;
	private Map<String, Object> customProperties;


	public String getClassName() {
		return className;
	}


	public SetClassCustomPropertiesStatement setClassName(String className) {
		this.className = className;
		return this;
	}


	public Map<String, Object> getCustomProperties() {
		return customProperties;
	}


	public SetClassCustomPropertiesStatement setCustomProperties(Map<String, Object> customProperties) {
		this.customProperties = customProperties;
		return this;
	}
}
