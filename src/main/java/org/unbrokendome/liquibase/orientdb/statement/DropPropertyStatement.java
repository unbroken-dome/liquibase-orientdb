package org.unbrokendome.liquibase.orientdb.statement;

import liquibase.statement.AbstractSqlStatement;

public class DropPropertyStatement extends AbstractSqlStatement {

	private String className;
	private String propertyName;


	public String getClassName() {
		return className;
	}


	public DropPropertyStatement setClassName(String className) {
		this.className = className;
		return this;
	}


	public String getPropertyName() {
		return propertyName;
	}


	public DropPropertyStatement setPropertyName(String propertyName) {
		this.propertyName = propertyName;
		return this;
	}
}
