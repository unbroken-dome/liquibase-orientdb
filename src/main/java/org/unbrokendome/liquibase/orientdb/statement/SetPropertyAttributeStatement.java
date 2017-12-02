package org.unbrokendome.liquibase.orientdb.statement;

import org.unbrokendome.liquibase.orientdb.common.OrientPropertyAttribute;

import liquibase.statement.AbstractSqlStatement;


public class SetPropertyAttributeStatement extends AbstractSqlStatement {

	private String className;
	private String propertyName;
	private OrientPropertyAttribute attributeName;
	private Object attributeValue;


	public String getClassName() {
		return className;
	}


	public SetPropertyAttributeStatement setClassName(String className) {
		this.className = className;
		return this;
	}


	public String getPropertyName() {
		return propertyName;
	}


	public SetPropertyAttributeStatement setPropertyName(String propertyName) {
		this.propertyName = propertyName;
		return this;
	}


	public OrientPropertyAttribute getAttributeName() {
		return attributeName;
	}


	public SetPropertyAttributeStatement setAttributeName(OrientPropertyAttribute attributeName) {
		this.attributeName = attributeName;
		return this;
	}


	public Object getAttributeValue() {
		return attributeValue;
	}


	public SetPropertyAttributeStatement setAttributeValue(Object attributeValue) {
		this.attributeValue = attributeValue;
		return this;
	}
}
