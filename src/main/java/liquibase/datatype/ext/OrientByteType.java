package liquibase.datatype.ext;

import org.unbrokendome.liquibase.orientdb.common.OrientPropertyType;

import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.LiquibaseDataType;


@DataTypeInfo(name = "byte", aliases = {"java.sql.Types.TINYINT", "java.lang.Byte" },
		minParameters = 0, maxParameters = 0, priority = LiquibaseDataType.PRIORITY_DATABASE)
public class OrientByteType extends OrientDataType {

	@Override
	public OrientPropertyType toOrientPropertyType() {
		return OrientPropertyType.BYTE;
	}
}
