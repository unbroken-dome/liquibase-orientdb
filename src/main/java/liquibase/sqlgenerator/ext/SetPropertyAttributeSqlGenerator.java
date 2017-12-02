package liquibase.sqlgenerator.ext;

import org.unbrokendome.liquibase.orientdb.sql.AlterPropertySql;
import org.unbrokendome.liquibase.orientdb.statement.SetPropertyAttributeStatement;

import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;


public class SetPropertyAttributeSqlGenerator extends AbstractSqlGenerator<SetPropertyAttributeStatement> {
	
	@Override
	public ValidationErrors validate(SetPropertyAttributeStatement statement, Database database,
			SqlGeneratorChain sqlGeneratorChain) {
		
		ValidationErrors validationErrors = new ValidationErrors();
		
		validationErrors.checkRequiredField("className", statement.getPropertyName());
		validationErrors.checkRequiredField("propertyName", statement.getPropertyName());
		validationErrors.checkRequiredField("attribute", statement.getAttributeName());
		validationErrors.checkRequiredField("value", statement.getAttributeValue());
		
		return validationErrors;
	}
	
	
	@Override
	public Sql[] generateSql(SetPropertyAttributeStatement statement, Database database,
			SqlGeneratorChain sqlGeneratorChain) {
		
		return new Sql[] {
				new AlterPropertySql(statement.getClassName(), statement.getPropertyName(),
						statement.getAttributeName().toString(),
						statement.getAttributeValue())
		};
	}
}
