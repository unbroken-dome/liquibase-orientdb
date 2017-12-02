package org.unbrokendome.liquibase.orientdb.structure;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import org.unbrokendome.liquibase.orientdb.common.OrientPropertyType;

import liquibase.structure.DatabaseObject;


public class OrientProperty extends AbstractOrientDatabaseObject<OrientProperty> {

	private OrientClass orientClass;
	private OrientPropertyType type;
	private OrientPropertyType linkedType;
	private OrientClass linkedClass;
	private boolean unsafe = false;
	

	public OrientProperty(OrientClass orientClass, String name) {
		super(name);
		this.orientClass = orientClass;
	}


	public String getQualifiedName() {
		if (orientClass != null && orientClass.getName() != null && getName() != null) {
			return orientClass.getName() + '.' + getName();
		}
		return null;
	}


	@Override
	public DatabaseObject[] getContainingObjects() {
		return new DatabaseObject[] { getOrientClass() };
	}


	public OrientClass getOrientClass() {
		return orientClass;
	}


	public OrientProperty setOrientClass(OrientClass orientClass) {
		this.orientClass = orientClass;
		return this;
	}


	public OrientPropertyType getType() {
		return type;
	}


	public OrientProperty setType(OrientPropertyType type) {
		this.type = type;
		return this;
	}


	public OrientPropertyType getLinkedType() {
		return linkedType;
	}


	public OrientProperty setLinkedType(OrientPropertyType linkedType) {
		this.linkedType = linkedType;
		return this;
	}


	public OrientClass getLinkedClass() {
		return linkedClass;
	}


	public OrientProperty setLinkedClass(OrientClass linkedClass) {
		this.linkedClass = linkedClass;
		return this;
	}


	public boolean isUnsafe() {
		return unsafe;
	}


	public OrientProperty setUnsafe(boolean unsafe) {
		this.unsafe = unsafe;
		return this;
	}


	@Override
	protected boolean equals(OrientProperty other) {
		return new EqualsBuilder().appendSuper(super.equals(other)).append(getOrientClass(), other.getOrientClass())
				.isEquals();
	}


	@Override
	public int hashCode() {
		return new HashCodeBuilder().appendSuper(super.hashCode()).append(getOrientClass()).toHashCode();
	}
}
