package org.unbrokendome.liquibase.orientdb.statement;

import liquibase.statement.AbstractSqlStatement;

public class RenamePropertyStatement extends AbstractSqlStatement {

	private String className;
	private String propertyName;
	private String newPropertyName;


	public String getClassName() {
		return className;
	}


	public RenamePropertyStatement setClassName(String className) {
		this.className = className;
		return this;
	}


	public String getPropertyName() {
		return propertyName;
	}


	public RenamePropertyStatement setPropertyName(String propertyName) {
		this.propertyName = propertyName;
		return this;
	}


	public String getNewPropertyName() {
		return newPropertyName;
	}


	public RenamePropertyStatement setNewPropertyName(String newPropertyName) {
		this.newPropertyName = newPropertyName;
		return this;
	}
}
