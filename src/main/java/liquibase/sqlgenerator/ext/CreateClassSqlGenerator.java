package liquibase.sqlgenerator.ext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;

import org.unbrokendome.liquibase.orientdb.sql.CreateClassSql;
import org.unbrokendome.liquibase.orientdb.statement.CreateClassStatement;
import org.unbrokendome.liquibase.orientdb.structure.OrientClass;


public class CreateClassSqlGenerator extends AbstractSqlGenerator<CreateClassStatement> {

	private static final Pattern CLASS_NAME_PATTERN = Pattern.compile("[A-Za-z][A-Za-z0-9_-]*");
	
	
	@Override
	public ValidationErrors validate(CreateClassStatement statement, Database database,
			SqlGeneratorChain sqlGeneratorChain) {
		ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("className", statement.getClassName());
        
        Matcher matcher = CLASS_NAME_PATTERN.matcher(statement.getClassName());
        if (!matcher.matches()) {
        	validationErrors.addError("Class name must start with a letter and consist only of alphanumeric "
        			+ "characters, underscores and/or dashes");
        }
        
        return validationErrors;
	}
	
	
	@Override
	public Sql[] generateSql(CreateClassStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
		
		OrientClass orientClass = new OrientClass(statement.getClassName())
				.setSuperClass(statement.getSuperClassName())
				.setClustersFromClusterIds(statement.getClusterIds())
				.setAbstract(statement.isAbstract());
		
		return new Sql[] { new CreateClassSql(orientClass) };
	}
}
