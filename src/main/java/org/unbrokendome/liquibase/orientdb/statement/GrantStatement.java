package org.unbrokendome.liquibase.orientdb.statement;

import liquibase.statement.AbstractSqlStatement;

public class GrantStatement extends AbstractSqlStatement {

	private String permission;
	private String resource;
	private String role;


	public String getPermission() {
		return permission;
	}


	public GrantStatement setPermission(String permission) {
		this.permission = permission;
		return this;
	}


	public String getResource() {
		return resource;
	}


	public GrantStatement setResource(String resource) {
		this.resource = resource;
		return this;
	}


	public String getRole() {
		return role;
	}


	public GrantStatement setRole(String role) {
		this.role = role;
		return this;
	}
}
