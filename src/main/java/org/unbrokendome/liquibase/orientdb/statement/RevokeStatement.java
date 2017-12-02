package org.unbrokendome.liquibase.orientdb.statement;

import liquibase.statement.AbstractSqlStatement;

public class RevokeStatement extends AbstractSqlStatement {

	private String permission;
	private String resource;
	private String role;


	public String getPermission() {
		return permission;
	}


	public RevokeStatement setPermission(String permission) {
		this.permission = permission;
		return this;
	}


	public String getResource() {
		return resource;
	}


	public RevokeStatement setResource(String resource) {
		this.resource = resource;
		return this;
	}


	public String getRole() {
		return role;
	}


	public RevokeStatement setRole(String role) {
		this.role = role;
		return this;
	}
}
