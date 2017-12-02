package liquibase.datatype.ext;

import org.unbrokendome.liquibase.orientdb.common.OrientPropertyType;

import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.LiquibaseDataType;


@DataTypeInfo(name = "decimal", aliases = { "java.sql.Types.DECIMAL", "java.math.BigDecimal" },
		minParameters = 0, maxParameters = 0, priority = LiquibaseDataType.PRIORITY_DATABASE)
public class OrientDecimalType extends OrientDataType {

	@Override
	public OrientPropertyType toOrientPropertyType() {
		return OrientPropertyType.DECIMAL;
	}
}
