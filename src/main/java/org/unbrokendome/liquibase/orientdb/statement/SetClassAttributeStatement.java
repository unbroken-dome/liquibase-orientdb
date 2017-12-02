package org.unbrokendome.liquibase.orientdb.statement;

import liquibase.statement.AbstractSqlStatement;

import org.unbrokendome.liquibase.orientdb.common.OrientClassAttribute;

public class SetClassAttributeStatement extends AbstractSqlStatement {

	private String className;
	private OrientClassAttribute attribute;
	private Object attributeValue;


	public String getClassName() {
		return className;
	}


	public SetClassAttributeStatement setClassName(String className) {
		this.className = className;
		return this;
	}


	public OrientClassAttribute getAttribute() {
		return attribute;
	}


	public SetClassAttributeStatement setAttribute(OrientClassAttribute attribute) {
		this.attribute = attribute;
		return this;
	}


	public Object getAttributeValue() {
		return attributeValue;
	}


	public SetClassAttributeStatement setAttributeValue(Object attributeValue) {
		this.attributeValue = attributeValue;
		return this;
	}
}
