package org.unbrokendome.liquibase.orientdb.sql;

import java.util.Collection;
import java.util.Collections;

import liquibase.structure.DatabaseObject;

import org.unbrokendome.liquibase.orientdb.structure.OrientCluster;


public class AlterClusterSql extends AbstractOrientSql {

	private final String clusterNameOrId;
	private final String attributeName;
	private final Object attributeValue;

	
	public AlterClusterSql(String clusterNameOrId, String attributeName, Object attributeValue) {
		this.clusterNameOrId = clusterNameOrId;
		this.attributeName = attributeName;
		this.attributeValue = attributeValue;
	}


	@Override
	public String toSql() {

		String attributeValueQuoted;
		if (attributeValue instanceof String || attributeValue instanceof Enum<?>) {
			attributeValueQuoted = "\"" + attributeValue + "\"";
		} else {
			attributeValueQuoted = attributeValue.toString();
		}

		return "ALTER CLUSTER " + clusterNameOrId + " " + attributeName + " " + attributeValueQuoted;
	}


	@Override
	public Collection<? extends DatabaseObject> getAffectedDatabaseObjects() {
		return Collections.singleton(new OrientCluster(clusterNameOrId));
	}
}
