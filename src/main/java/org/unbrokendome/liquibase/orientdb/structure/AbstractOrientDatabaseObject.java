package org.unbrokendome.liquibase.orientdb.structure;

import liquibase.structure.AbstractDatabaseObject;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Schema;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


public abstract class AbstractOrientDatabaseObject<T extends AbstractOrientDatabaseObject<T>> extends AbstractDatabaseObject {

	private String name;
	
	
	public AbstractOrientDatabaseObject() {
	}
	
	
	public AbstractOrientDatabaseObject(String name) {
		setName(name);
	}


	@Override
	public String getName() {
		return name;
	}


	@SuppressWarnings("unchecked")
	@Override
	public T setName(String name) {
		this.name = name;
		setAttribute("name", name);
		return (T) this;
	}


	@Override
	public Schema getSchema() {
		return null;
	}
	
	
	@Override
	public DatabaseObject[] getContainingObjects() {
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		return obj == this
				|| (obj != null && obj.getClass() == this.getClass() && equals((T) obj));
	}
	
	
	protected boolean equals(T other) {
		return new EqualsBuilder()
				.append(getName(), other.getName())
				.isEquals();
	}
	
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(getName())
				.toHashCode();
	}
}
