package liquibase.datatype.ext;

import org.unbrokendome.liquibase.orientdb.common.OrientPropertyType;

import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.LiquibaseDataType;


@DataTypeInfo(name = "datetime", aliases = {"java.sql.Types.DATETIME", "java.util.Date" },
		minParameters = 0, maxParameters = 0, priority = LiquibaseDataType.PRIORITY_DATABASE)
public class OrientDateTimeType extends OrientDataType {

	@Override
	public OrientPropertyType toOrientPropertyType() {
		return OrientPropertyType.DATETIME;
	}
}
