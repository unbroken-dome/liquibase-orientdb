package org.unbrokendome.liquibase.orientdb.sql;

import java.util.Collection;
import java.util.Collections;

import liquibase.structure.DatabaseObject;

import org.unbrokendome.liquibase.orientdb.structure.OrientCluster;


public class DropClusterSql extends AbstractOrientSql {

	private final OrientCluster cluster;


	public DropClusterSql(OrientCluster cluster) {
		this.cluster = cluster;
	}


	@Override
	public String toSql() {
		return "DROP CLUSTER " + cluster.getName();
	}


	@Override
	public Collection<? extends DatabaseObject> getAffectedDatabaseObjects() {
		return Collections.singleton(cluster);
	}
}
