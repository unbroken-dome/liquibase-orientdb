package org.unbrokendome.liquibase.orientdb.statement;

import java.util.List;

import liquibase.statement.AbstractSqlStatement;


public class CreateClassStatement extends AbstractSqlStatement {
	
	private String className;
	private String superClassName;
	private List<String> clusterIds;
	private boolean isAbstract;
	
	
	public String getClassName() {
		return className;
	}
	
	
	public CreateClassStatement setClassName(String className) {
		this.className = className;
		return this;
	}
	
	
	public String getSuperClassName() {
		return superClassName;
	}
	
	
	public CreateClassStatement setSuperClassName(String superClassName) {
		this.superClassName = superClassName;
		return this;
	}
	
	
	public List<String> getClusterIds() {
		return clusterIds;
	}
	
	
	public CreateClassStatement setClusterIds(List<String> clusterIds) {
		this.clusterIds = clusterIds;
		return this;
	}
	
	
	public boolean isAbstract() {
		return isAbstract;
	}
	
	
	public CreateClassStatement setAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
		return this;
	}
}
