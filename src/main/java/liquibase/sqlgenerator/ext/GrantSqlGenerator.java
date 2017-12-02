package liquibase.sqlgenerator.ext;

import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import org.unbrokendome.liquibase.orientdb.common.OrientPermission;
import org.unbrokendome.liquibase.orientdb.sql.GrantSql;
import org.unbrokendome.liquibase.orientdb.statement.GrantStatement;


public class GrantSqlGenerator extends AbstractSqlGenerator<GrantStatement> {

    @Override
    public ValidationErrors validate(GrantStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();

        validationErrors.checkRequiredField("permission", statement.getPermission());
        if (!isValidPermission(statement.getPermission())) {
            validationErrors.addError("Permission \"" + statement.getPermission() + "\" is not a valid permission");
        }

        validationErrors.checkRequiredField("resource", statement.getResource());
        validationErrors.checkRequiredField("role", statement.getRole());
        return validationErrors;
    }


    private boolean isValidPermission(String permission) {
        if (permission == null) {
            return true;
        }
        try {
            OrientPermission.valueOf(permission.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }


    @Override
    public Sql[] generateSql(GrantStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {

        OrientPermission permission = OrientPermission.valueOf(statement.getPermission().toUpperCase());

        return new Sql[] {
            new GrantSql(permission, statement.getResource(), statement.getRole() )
        };
    }
}
