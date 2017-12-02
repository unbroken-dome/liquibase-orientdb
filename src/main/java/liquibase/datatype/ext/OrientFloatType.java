package liquibase.datatype.ext;

import org.unbrokendome.liquibase.orientdb.common.OrientPropertyType;

import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.LiquibaseDataType;


@DataTypeInfo(name = "float", aliases = {"java.sql.Types.FLOAT", "java.lang.Float" },
		minParameters = 0, maxParameters = 0, priority = LiquibaseDataType.PRIORITY_DATABASE)
public class OrientFloatType extends OrientDataType {
	
	@Override
	public OrientPropertyType toOrientPropertyType() {
		return OrientPropertyType.FLOAT;
	}
}
