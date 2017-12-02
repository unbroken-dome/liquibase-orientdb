package org.unbrokendome.liquibase.orientdb.statement;

import liquibase.statement.AbstractSqlStatement;

public class DropClusterStatement extends AbstractSqlStatement {

	private String clusterNameOrId;


	public String getClusterNameOrId() {
		return clusterNameOrId;
	}


	public DropClusterStatement setClusterNameOrId(String clusterNameOrId) {
		this.clusterNameOrId = clusterNameOrId;
		return this;
	}
}
