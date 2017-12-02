package org.unbrokendome.liquibase.orientdb.statement;

import liquibase.statement.AbstractSqlStatement;

public class DropClassStatement extends AbstractSqlStatement {

	private String className;
	private boolean unsafe = false;


	public String getClassName() {
		return className;
	}


	public DropClassStatement setClassName(String className) {
		this.className = className;
		return this;
	}


	public boolean isUnsafe() {
		return unsafe;
	}


	public DropClassStatement setUnsafe(boolean unsafe) {
		this.unsafe = unsafe;
		return this;
	}
}
