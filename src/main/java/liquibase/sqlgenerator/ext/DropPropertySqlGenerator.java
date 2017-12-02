package liquibase.sqlgenerator.ext;

import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import org.unbrokendome.liquibase.orientdb.sql.DropPropertySql;
import org.unbrokendome.liquibase.orientdb.statement.DropPropertyStatement;
import org.unbrokendome.liquibase.orientdb.structure.OrientClass;
import org.unbrokendome.liquibase.orientdb.structure.OrientProperty;


public class DropPropertySqlGenerator extends AbstractSqlGenerator<DropPropertyStatement> {

    @Override
    public ValidationErrors validate(DropPropertyStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("className", statement.getClassName());
        validationErrors.checkRequiredField("propertyName", statement.getPropertyName());
        return validationErrors;
    }


    @Override
    public Sql[] generateSql(DropPropertyStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {

        OrientClass orientClass = new OrientClass(statement.getClassName());
        OrientProperty property = new OrientProperty(orientClass, statement.getPropertyName());

        return new Sql[] { new DropPropertySql(property) };
    }
}
