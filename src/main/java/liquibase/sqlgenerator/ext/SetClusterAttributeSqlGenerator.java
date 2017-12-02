package liquibase.sqlgenerator.ext;

import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import org.unbrokendome.liquibase.orientdb.sql.AlterClusterSql;
import org.unbrokendome.liquibase.orientdb.statement.SetClusterAttributeStatement;


public class SetClusterAttributeSqlGenerator extends AbstractSqlGenerator<SetClusterAttributeStatement> {
	
	@Override
	public ValidationErrors validate(SetClusterAttributeStatement statement, Database database,
			SqlGeneratorChain sqlGeneratorChain) {
		
		ValidationErrors validationErrors = new ValidationErrors();
		
		validationErrors.checkRequiredField("clusterNameOrId", statement.getClusterNameOrId());
		validationErrors.checkRequiredField("attribute", statement.getAttribute());
		validationErrors.checkRequiredField("value", statement.getAttributeValue());
		
		return validationErrors;
	}
	
	
	@Override
	public Sql[] generateSql(SetClusterAttributeStatement statement, Database database,
			SqlGeneratorChain sqlGeneratorChain) {
		
		return new Sql[] {
				new AlterClusterSql(statement.getClusterNameOrId(), statement.getAttribute().toString(),
						statement.getAttributeValue())
		};
	}
}
