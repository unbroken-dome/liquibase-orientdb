package org.unbrokendome.liquibase.orientdb.statement;

import org.unbrokendome.liquibase.orientdb.common.OrientClusterAttribute;

import liquibase.statement.AbstractSqlStatement;


public class SetClusterAttributeStatement extends AbstractSqlStatement {

	private String clusterNameOrId;
	private OrientClusterAttribute attribute;
	private Object attributeValue;


	public String getClusterNameOrId() {
		return clusterNameOrId;
	}


	public SetClusterAttributeStatement setClusterNameOrId(String clusterNameOrId) {
		this.clusterNameOrId = clusterNameOrId;
		return this;
	}


	public OrientClusterAttribute getAttribute() {
		return attribute;
	}


	public SetClusterAttributeStatement setAttribute(OrientClusterAttribute attribute) {
		this.attribute = attribute;
		return this;
	}


	public Object getAttributeValue() {
		return attributeValue;
	}


	public SetClusterAttributeStatement setAttributeValue(Object attributeValue) {
		this.attributeValue = attributeValue;
		return this;
	}
}
