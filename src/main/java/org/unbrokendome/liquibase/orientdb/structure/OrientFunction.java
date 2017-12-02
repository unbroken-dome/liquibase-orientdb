package org.unbrokendome.liquibase.orientdb.structure;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.ImmutableList;

public class OrientFunction extends AbstractOrientDatabaseObject<OrientFunction> {

	private String code;
	private String language;
	private List<String> parameterNames = Collections.emptyList();
	private Boolean idempotent;


	public String getCode() {
		return code;
	}


	public OrientFunction setCode(String code) {
		this.code = code;
		return this;
	}


	public String getLanguage() {
		return language;
	}


	public OrientFunction setLanguage(String language) {
		this.language = language;
		return this;
	}


	public List<String> getParameterNames() {
		return parameterNames;
	}


	public OrientFunction setParameterNames(Iterable<String> parameterNames) {
		this.parameterNames = ImmutableList.copyOf(parameterNames);
		return this;
	}


	public Boolean getIdempotent() {
		return idempotent;
	}


	public OrientFunction setIdempotent(boolean idempotent) {
		this.idempotent = idempotent;
		return this;
	}

}
