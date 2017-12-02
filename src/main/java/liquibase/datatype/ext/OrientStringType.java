package liquibase.datatype.ext;

import liquibase.database.Database;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.LiquibaseDataType;

import org.unbrokendome.liquibase.orientdb.common.OrientPropertyType;


@DataTypeInfo(name = "string", aliases = { "varchar", "java.sql.Types.VARCHAR", "java.lang.String" },
minParameters = 0, maxParameters = 0, priority = LiquibaseDataType.PRIORITY_DATABASE)
public class OrientStringType extends OrientDataType {

	@Override
	public OrientPropertyType toOrientPropertyType() {
		return OrientPropertyType.STRING;
	}
	
	
	@Override
	public String objectToSql(Object value, Database database) {
	
		String stringValue = (String) value;
		return "'" + database.escapeStringForDatabase(stringValue) + "'";
	}
}
