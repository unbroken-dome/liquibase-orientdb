package liquibase.datatype.ext;

import liquibase.datatype.LiquibaseDataType;

import org.unbrokendome.liquibase.orientdb.common.OrientPropertyType;


public abstract class OrientDataType extends LiquibaseDataType {

	public abstract OrientPropertyType toOrientPropertyType();
}
