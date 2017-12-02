package org.unbrokendome.liquibase.orientdb.sql;

import java.util.Collection;
import java.util.Collections;

import liquibase.structure.DatabaseObject;

import org.unbrokendome.liquibase.orientdb.structure.OrientProperty;


public class DropPropertySql extends AbstractOrientSql {

	private final OrientProperty property;
	
	
	public DropPropertySql(OrientProperty property) {
		this.property = property;
	}


	@Override
	public String toSql() {
		return "DROP PROPERTY " + property.getQualifiedName();
	}
	
	
	@Override
	public Collection<? extends DatabaseObject> getAffectedDatabaseObjects() {
		return Collections.singleton(property);
	}
}
