package org.unbrokendome.liquibase.orientdb.sql;

import java.util.Collection;
import java.util.Collections;

import liquibase.structure.DatabaseObject;

import org.unbrokendome.liquibase.orientdb.structure.OrientClass;
import org.unbrokendome.liquibase.orientdb.structure.OrientProperty;


public class AlterPropertySql extends AbstractOrientSql {

	private final String className;
	private final String propertyName;
	private final String attributeName;
	private final Object attributeValue;


	public AlterPropertySql(String className, String propertyName, String attributeName, Object attributeValue) {
		this.className = className;
		this.propertyName = propertyName;
		this.attributeName = attributeName;
		this.attributeValue = attributeValue;
	}


	@Override
	public String toSql() {

		String attributeValueQuoted;
		if (attributeValue instanceof String || attributeValue instanceof Enum<?>) {
			attributeValueQuoted = "\"" + attributeValue + "\"";
		} else {
			attributeValueQuoted = attributeValue.toString();
		}

		return "ALTER PROPERTY " + className + "." + propertyName
				+ " " + attributeName + " " + attributeValueQuoted;
	}

	
	@Override
	public Collection<? extends DatabaseObject> getAffectedDatabaseObjects() {
		return Collections.singleton(
				new OrientProperty(new OrientClass(className), propertyName));
						
	}
}
