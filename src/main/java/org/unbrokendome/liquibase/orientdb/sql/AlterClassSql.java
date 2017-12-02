package org.unbrokendome.liquibase.orientdb.sql;

import java.util.Collection;
import java.util.Collections;

import liquibase.structure.DatabaseObject;

import org.unbrokendome.liquibase.orientdb.structure.OrientClass;


public class AlterClassSql extends AbstractOrientSql {

	private final String className;
	private final String attributeName;
	private final Object attributeValue;

	
	public AlterClassSql(String className, String attributeName, Object attributeValue) {
		this.className = className;
		this.attributeName = attributeName;
		this.attributeValue = attributeValue;
	}
	

	@Override
	public String toSql() {
		return "ALTER CLASS " + className + " " + attributeName + " " + attributeValue;
	}

	
	@Override
	public Collection<? extends DatabaseObject> getAffectedDatabaseObjects() {
		return Collections.singleton(new OrientClass(className));
	}
}
