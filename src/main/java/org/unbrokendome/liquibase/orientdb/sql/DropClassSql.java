package org.unbrokendome.liquibase.orientdb.sql;

import java.util.Collection;
import java.util.Collections;

import liquibase.structure.DatabaseObject;

import org.unbrokendome.liquibase.orientdb.structure.OrientClass;


public class DropClassSql extends AbstractOrientSql {

	private final OrientClass orientClass;
	private final boolean unsafe;


	public DropClassSql(OrientClass orientClass, boolean unsafe) {
		this.orientClass = orientClass;
		this.unsafe = unsafe;
	}


	@Override
	public String toSql() {
		StringBuilder builder = new StringBuilder()
				.append("DROP CLASS ")
				.append(orientClass.getName());

		if (unsafe) {
			builder.append(" UNSAFE");
		}
		return builder.toString();
	}


	@Override
	public Collection<? extends DatabaseObject> getAffectedDatabaseObjects() {
		return Collections.singleton(orientClass);
	}
}
