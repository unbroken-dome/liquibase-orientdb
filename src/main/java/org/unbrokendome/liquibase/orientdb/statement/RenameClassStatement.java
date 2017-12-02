package org.unbrokendome.liquibase.orientdb.statement;

import liquibase.statement.AbstractSqlStatement;

public class RenameClassStatement extends AbstractSqlStatement {

	private String className;
	private String newName;
	private String newShortName;


	public String getClassName() {
		return className;
	}


	public RenameClassStatement setClassName(String className) {
		this.className = className;
		return this;
	}


	public String getNewName() {
		return newName;
	}


	public RenameClassStatement setNewName(String newName) {
		this.newName = newName;
		return this;
	}


	public String getNewShortName() {
		return newShortName;
	}


	public RenameClassStatement setNewShortName(String newShortName) {
		this.newShortName = newShortName;
		return this;
	}
}
