package org.unbrokendome.liquibase.orientdb.sql;

import java.util.Collection;
import java.util.Collections;

import liquibase.structure.DatabaseObject;

import org.unbrokendome.liquibase.orientdb.structure.OrientIndex;


public class DropIndexSql extends AbstractOrientSql {

	private final OrientIndex index;


	public DropIndexSql(OrientIndex index) {
		this.index = index;
	}


	@Override
	public String toSql() {
		return "DROP INDEX " + index.getName();
	}


	@Override
	public Collection<? extends DatabaseObject> getAffectedDatabaseObjects() {
		return Collections.singleton(index);
	}
}
