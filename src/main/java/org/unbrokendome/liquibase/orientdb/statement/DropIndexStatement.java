package org.unbrokendome.liquibase.orientdb.statement;

import liquibase.statement.AbstractSqlStatement;

public class DropIndexStatement extends AbstractSqlStatement {

	private String indexName;


	public String getIndexName() {
		return indexName;
	}


	public DropIndexStatement setIndexName(String indexName) {
		this.indexName = indexName;
		return this;
	}
}
