package org.unbrokendome.liquibase.orientdb.sql;

import java.util.Collection;
import java.util.Collections;

import liquibase.structure.DatabaseObject;

import org.unbrokendome.liquibase.orientdb.structure.OrientCluster;


public class CreateClusterSql extends AbstractOrientSql {

	private final OrientCluster cluster;

	
	public CreateClusterSql(OrientCluster cluster) {
		this.cluster = cluster;
	}


	@Override
	public String toSql() {
		StringBuilder builder = new StringBuilder();
		builder.append("CREATE CLUSTER ")
				.append(cluster.getName());
		if (cluster.getClusterId() != null) {
			builder.append(" ID ")
					.append(cluster.getClusterId());
		}
		return builder.toString();
	}

	
	@Override
	public Collection<? extends DatabaseObject> getAffectedDatabaseObjects() {
		return Collections.singleton(cluster);
	}
}
