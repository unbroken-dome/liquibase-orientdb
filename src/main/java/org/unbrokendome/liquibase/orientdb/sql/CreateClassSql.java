package org.unbrokendome.liquibase.orientdb.sql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import liquibase.structure.DatabaseObject;

import org.unbrokendome.liquibase.orientdb.structure.AbstractOrientDatabaseObject;
import org.unbrokendome.liquibase.orientdb.structure.OrientClass;
import org.unbrokendome.liquibase.orientdb.structure.OrientCluster;


public class CreateClassSql extends AbstractOrientSql {

	private final OrientClass orientClass;

	
	public CreateClassSql(OrientClass orientClass) {
		this.orientClass = orientClass;
	}


	@Override
	public String toSql() {
		
		StringBuilder builder = new StringBuilder();
		builder.append("CREATE CLASS ")
				.append(orientClass.getName());
		
		if (orientClass.getSuperClass() != null) {
			builder.append(" EXTENDS ")
					.append(orientClass.getSuperClass().getName());
		}
		
		List<OrientCluster> clusters = orientClass.getClusters();
		if (clusters != null && !clusters.isEmpty()) {
			builder.append(" CLUSTER ")
					.append(clusters.stream()
							.map(AbstractOrientDatabaseObject::getName)
							.collect(Collectors.joining(",")));
		}
		
		if (orientClass.isAbstract()) {
			builder.append(" ABSTRACT");
		}
		
		return builder.toString();
	}


	@Override
	public Collection<? extends DatabaseObject> getAffectedDatabaseObjects() {
		List<DatabaseObject> objects = new ArrayList<>();
		objects.add(orientClass);
		if (orientClass.getClusters() != null) {
			objects.addAll(orientClass.getClusters());
		}
		return Collections.unmodifiableList(objects);
	}
}
