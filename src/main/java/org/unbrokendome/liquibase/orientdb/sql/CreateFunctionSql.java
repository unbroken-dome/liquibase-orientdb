package org.unbrokendome.liquibase.orientdb.sql;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import liquibase.structure.DatabaseObject;

import org.apache.commons.lang3.StringUtils;

import org.unbrokendome.liquibase.orientdb.structure.OrientFunction;

public class CreateFunctionSql extends AbstractOrientSql {

	private final OrientFunction function;


	public CreateFunctionSql(OrientFunction function) {
		this.function = function;
	}


	@Override
	public String toSql() {
		
		StringBuilder builder = new StringBuilder()
				.append("CREATE FUNCTION ")
				.append(function.getName())
				.append(" ")
				.append(getCodeQuoted());
		
		List<String> parameterNames = function.getParameterNames();
		if (parameterNames != null && !parameterNames.isEmpty()) {
			builder.append(" PARAMETERS ")
					.append(String.join(",", parameterNames));
		}
		
		if (function.getIdempotent() != null) {
			builder.append(" IDEMPOTENT ")
					.append(function.getIdempotent().booleanValue());
		}
		
		if (function.getLanguage() != null) {
			builder.append(" LANGUAGE ")
					.append(function.getLanguage());
		}
		
		return builder.toString();
	}
	
	
	private String getCodeQuoted() {
		return '"' + StringUtils.replace(function.getCode(), "\"", "\\\"") + '"';
	}
	
	
	@Override
	public Collection<? extends DatabaseObject> getAffectedDatabaseObjects() {
		return Collections.singleton(function);
	}
}
