package liquibase.sqlgenerator.ext;

import liquibase.database.Database;
import liquibase.database.ext.OrientDatabase;
import liquibase.datatype.DataTypeFactory;
import liquibase.datatype.LiquibaseDataType;
import liquibase.datatype.ext.OrientDataType;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import liquibase.statement.core.ModifyDataTypeStatement;
import org.unbrokendome.liquibase.orientdb.common.OrientPropertyAttribute;
import org.unbrokendome.liquibase.orientdb.sql.AlterPropertySql;


public class OrientModifyDataTypeSqlGenerator extends AbstractSqlGenerator<ModifyDataTypeStatement> {

    @Override
    public boolean supports(ModifyDataTypeStatement statement, Database database) {
        return database instanceof OrientDatabase;
    }


    @Override
    public int getPriority() {
        return PRIORITY_DATABASE;
    }



    @Override
    public ValidationErrors validate(ModifyDataTypeStatement statement, Database database,
                                     SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();

        LiquibaseDataType dataType = DataTypeFactory.getInstance().fromDescription(statement.getNewDataType(), database);
        if (!(dataType instanceof OrientDataType)) {
            validationErrors.addError("Column type \"" + statement.getNewDataType() +
                    "\" is not supported in OrientDB");
        }

        return validationErrors;
    }


    @Override
    public Sql[] generateSql(ModifyDataTypeStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {

        LiquibaseDataType dataType = DataTypeFactory.getInstance().fromDescription(
                statement.getNewDataType(), database);

        AlterPropertySql sql = new AlterPropertySql(statement.getTableName(),
                statement.getColumnName(),
                OrientPropertyAttribute.TYPE.toString(),
                dataType.toString());

        return new Sql[] { sql };
    }
}
