package org.unbrokendome.liquibase.orientdb.statement;

import liquibase.statement.AbstractSqlStatement;

public class ChangePropertyTypeStatement extends AbstractSqlStatement {

	private String className;
	private String propertyName;
	private String type;
	private String linkedType;
	private String linkedClass;


	public String getClassName() {
		return className;
	}


	public ChangePropertyTypeStatement setClassName(String className) {
		this.className = className;
		return this;
	}


	public String getPropertyName() {
		return propertyName;
	}


	public ChangePropertyTypeStatement setPropertyName(String propertyName) {
		this.propertyName = propertyName;
		return this;
	}


	public String getType() {
		return type;
	}


	public ChangePropertyTypeStatement setType(String type) {
		this.type = type;
		return this;
	}


	public String getLinkedType() {
		return linkedType;
	}


	public ChangePropertyTypeStatement setLinkedType(String linkedType) {
		this.linkedType = linkedType;
		return this;
	}


	public String getLinkedClass() {
		return linkedClass;
	}


	public ChangePropertyTypeStatement setLinkedClass(String linkedClass) {
		this.linkedClass = linkedClass;
		return this;
	}
}
