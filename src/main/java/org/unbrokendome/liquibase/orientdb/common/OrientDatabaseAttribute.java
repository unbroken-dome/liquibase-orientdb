package org.unbrokendome.liquibase.orientdb.common;


public enum OrientDatabaseAttribute {
	DEFAULTCLUSTERID(String.class),
	DATEFORMAT(String.class),
	DATETIMEFORMAT(String.class),
	TIMEZONE(String.class),
	LOCALECOUNTRY(String.class),
	LOCALELANGUAGE(String.class),
	CHARSET(String.class),
	CLUSTERSELECTION(ClusterSelection.class),
	MINIMUMCLUSTERS(Integer.class),
	CONFLICTSTRATEGY(ConflictStrategy.class);
	
	
	private Class<?> valueType;

	
	OrientDatabaseAttribute(Class<?> valueType) {
		this.valueType = valueType;
	}
	
	
	
	public Class<?> getValueType() {
		return valueType;
	}
}
