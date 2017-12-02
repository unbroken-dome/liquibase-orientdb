package org.unbrokendome.liquibase.orientdb.sql;

import java.util.Collection;
import java.util.Collections;

import liquibase.sql.Sql;
import liquibase.structure.DatabaseObject;


public abstract class AbstractOrientSql implements Sql {

	@Override
	public String getEndDelimiter() {
		return ";";
	}
	
	
	@Override
	public String toString() {
		return toSql() + getEndDelimiter();
	}
	
	
	@Override
	public Collection<? extends DatabaseObject> getAffectedDatabaseObjects() {
		return Collections.emptyList();
	}
}
