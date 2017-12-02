package org.unbrokendome.liquibase.orientdb.statement;

import liquibase.statement.AbstractSqlStatement;


public class RemoveClusterStatement extends AbstractSqlStatement {
	
	private String className;
	private String clusterName;
	
	
	public String getClassName() {
		return className;
	}
	
	
	public RemoveClusterStatement setClassName(String className) {
		this.className = className;
		return this;
	}
	
	
	public String getClusterName() {
		return clusterName;
	}
	
	
	public RemoveClusterStatement setClusterName(String clusterName) {
		this.clusterName = clusterName;
		return this;
	}
}
