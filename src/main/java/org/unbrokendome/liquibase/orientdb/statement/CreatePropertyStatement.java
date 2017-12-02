package org.unbrokendome.liquibase.orientdb.statement;

import liquibase.statement.AbstractSqlStatement;

public class CreatePropertyStatement extends AbstractSqlStatement {

	private String className;
	private String propertyName;
	private String type;
	private String linkedType;
	private boolean unsafe;


	public String getClassName() {
		return className;
	}


	public CreatePropertyStatement setClassName(String className) {
		this.className = className;
		return this;
	}


	public String getPropertyName() {
		return propertyName;
	}


	public CreatePropertyStatement setPropertyName(String propertyName) {
		this.propertyName = propertyName;
		return this;
	}


	public String getType() {
		return type;
	}


	public CreatePropertyStatement setType(String type) {
		this.type = type;
		return this;
	}


	public String getLinkedType() {
		return linkedType;
	}


	public CreatePropertyStatement setLinkedType(String linkedType) {
		this.linkedType = linkedType;
		return this;
	}


	public boolean isUnsafe() {
		return unsafe;
	}


	public CreatePropertyStatement setUnsafe(boolean unsafe) {
		this.unsafe = unsafe;
		return this;
	}
}
