package liquibase.datatype.ext;

import org.unbrokendome.liquibase.orientdb.common.OrientPropertyType;

import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.LiquibaseDataType;


@DataTypeInfo(name = "double", aliases = {"java.sql.Types.DOUBLE", "java.lang.Double" },
		minParameters = 0, maxParameters = 0, priority = LiquibaseDataType.PRIORITY_DATABASE)
public class OrientDoubleType extends OrientDataType {

	@Override
	public OrientPropertyType toOrientPropertyType() {
		return OrientPropertyType.DOUBLE;
	}
}
