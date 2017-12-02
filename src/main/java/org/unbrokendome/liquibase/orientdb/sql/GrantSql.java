package org.unbrokendome.liquibase.orientdb.sql;

import org.unbrokendome.liquibase.orientdb.common.OrientPermission;

public class GrantSql extends AbstractOrientSql {

	private final OrientPermission permission;
	private final String resource;
	private final String role;


	public GrantSql(OrientPermission permission, String resource, String role) {
		this.permission = permission;
		this.resource = resource;
		this.role = role;
	}


	@Override
	public String toSql() {
		return "GRANT " + permission + " ON " + resource + " TO " + role;
	}
}
