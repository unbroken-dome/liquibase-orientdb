package org.unbrokendome.liquibase.orientdb.statement;

import java.util.List;


public class CreateManualIndexStatement extends AbstractCreateIndexStatement<CreateManualIndexStatement> {

	private List<String> keyTypes;


	public List<String> getKeyTypes() {
		return keyTypes;
	}


	public CreateManualIndexStatement setKeyTypes(List<String> keyTypes) {
		this.keyTypes = keyTypes;
		return this;
	}
}
