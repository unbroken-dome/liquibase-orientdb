package liquibase.datatype.ext;

import org.unbrokendome.liquibase.orientdb.common.OrientPropertyType;

import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.LiquibaseDataType;


@DataTypeInfo(name = "integer", aliases = { "java.sql.Types.INTEGER", "java.lang.Integer", "int" },
		minParameters = 0, maxParameters = 0, priority = LiquibaseDataType.PRIORITY_DATABASE)
public class OrientIntegerType extends OrientDataType {

	@Override
	public OrientPropertyType toOrientPropertyType() {
		return OrientPropertyType.INTEGER;
	}
}
