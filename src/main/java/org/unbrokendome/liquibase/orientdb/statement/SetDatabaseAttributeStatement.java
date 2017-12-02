package org.unbrokendome.liquibase.orientdb.statement;

import org.unbrokendome.liquibase.orientdb.common.OrientDatabaseAttribute;

import liquibase.statement.AbstractSqlStatement;

public class SetDatabaseAttributeStatement extends AbstractSqlStatement {

	private OrientDatabaseAttribute attributeName;
	private Object attributeValue;


	public OrientDatabaseAttribute getAttributeName() {
		return attributeName;
	}


	public SetDatabaseAttributeStatement setAttributeName(OrientDatabaseAttribute attributeName) {
		this.attributeName = attributeName;
		return this;
	}


	public Object getAttributeValue() {
		return attributeValue;
	}


	public SetDatabaseAttributeStatement setAttributeValue(Object attributeValue) {
		this.attributeValue = attributeValue;
		return this;
	}
}
