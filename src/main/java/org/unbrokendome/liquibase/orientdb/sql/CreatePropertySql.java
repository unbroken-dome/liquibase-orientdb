package org.unbrokendome.liquibase.orientdb.sql;

import java.util.Collection;
import java.util.Collections;

import liquibase.structure.DatabaseObject;

import org.unbrokendome.liquibase.orientdb.structure.OrientProperty;


public class CreatePropertySql extends AbstractOrientSql {

	private final OrientProperty property;
	
	
	public CreatePropertySql(OrientProperty property) {
		this.property = property;
	}


	@Override
	public String toSql() {
		StringBuilder builder = new StringBuilder();
		builder.append("CREATE PROPERTY ")
				.append(property.getQualifiedName())
				.append(" ")
				.append(property.getType());
		
		if (property.getLinkedType() != null) {
			builder.append(" ")
					.append(property.getLinkedType());
		
		} else if (property.getLinkedClass() != null) {
			builder.append(" ")
					.append(property.getLinkedClass().getName());
		}
		
		if (property.isUnsafe()) {
			builder.append(" UNSAFE");
		}
		
		return builder.toString();
	}


	@Override
	public Collection<? extends DatabaseObject> getAffectedDatabaseObjects() {
		return Collections.singleton(property);
	}
}
