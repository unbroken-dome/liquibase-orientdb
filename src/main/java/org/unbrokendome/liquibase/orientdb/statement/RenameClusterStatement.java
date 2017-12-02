package org.unbrokendome.liquibase.orientdb.statement;

import liquibase.statement.AbstractSqlStatement;

public class RenameClusterStatement extends AbstractSqlStatement {

	private String clusterNameOrId;
	private String newClusterName;


	public String getClusterNameOrId() {
		return clusterNameOrId;
	}


	public RenameClusterStatement setClusterNameOrId(String clusterNameOrId) {
		this.clusterNameOrId = clusterNameOrId;
		return this;
	}


	public String getNewClusterName() {
		return newClusterName;
	}


	public RenameClusterStatement setNewClusterName(String newClusterName) {
		this.newClusterName = newClusterName;
		return this;
	}
}
