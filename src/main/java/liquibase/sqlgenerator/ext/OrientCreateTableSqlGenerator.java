package liquibase.sqlgenerator.ext;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import liquibase.database.Database;
import liquibase.database.ext.OrientDatabase;
import liquibase.datatype.LiquibaseDataType;
import liquibase.datatype.ext.OrientDataType;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import liquibase.statement.core.CreateTableStatement;

import org.unbrokendome.liquibase.orientdb.common.OrientPropertyAttribute;
import org.unbrokendome.liquibase.orientdb.sql.AlterPropertySql;
import org.unbrokendome.liquibase.orientdb.sql.CreateClassSql;
import org.unbrokendome.liquibase.orientdb.sql.CreateIndexSql;
import org.unbrokendome.liquibase.orientdb.sql.CreatePropertySql;
import org.unbrokendome.liquibase.orientdb.structure.*;


public class OrientCreateTableSqlGenerator extends AbstractSqlGenerator<CreateTableStatement> {


    @Override
    public boolean supports(CreateTableStatement statement, Database database) {
        return database instanceof OrientDatabase;
    }


    @Override
    public int getPriority() {
        return PRIORITY_DATABASE;
    }


    @Override
    public ValidationErrors validate(CreateTableStatement statement, Database database,
                                     SqlGeneratorChain sqlGeneratorChain) {

        ValidationErrors validationErrors = new ValidationErrors();

        for (String columnName : statement.getColumns()) {
            LiquibaseDataType columnType = statement.getColumnTypes().get(columnName);
            if (!(columnType instanceof OrientDataType)) {
                validationErrors.addError("Column type " + columnType.getName() + " is not supported in OrientDB");
            }
        }

        return validationErrors;
    }


    @Override
    public Sql[] generateSql(CreateTableStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {

        List<Sql> sqlList = new ArrayList<>();

        OrientClass orientClass = new OrientClass(statement.getTableName());
        sqlList.add(new CreateClassSql(orientClass));

        for (String columnName : statement.getColumns()) {
            OrientDataType columnType = (OrientDataType) statement.getColumnTypes().get(columnName);

            OrientProperty property = new OrientProperty(orientClass, columnName)
                    .setType(columnType.toOrientPropertyType());
            sqlList.add(new CreatePropertySql(property));

            if (statement.getNotNullColumns().contains(columnName)) {
                sqlList.add(new AlterPropertySql(orientClass.getName(), columnName,
                        OrientPropertyAttribute.NOTNULL.toString(), "true"));
            }
        }

        if (statement.getPrimaryKeyConstraint() != null) {
            List<OrientIndexedProperty> indexedProperties =
                    statement.getPrimaryKeyConstraint().getColumns().stream()
                            .map(c -> new OrientIndexedProperty()
                                    .setProperty(new OrientProperty(orientClass, c)))
                            .collect(Collectors.toList());

            OrientIndex index = new OrientIndex()
                    .setOrientClass(orientClass)
                    .setIndexedProperties(indexedProperties)
                    .setType(OrientIndexType.UNIQUE);
            sqlList.add(new CreateIndexSql(index));
        }

        return sqlList.toArray(new Sql[sqlList.size()]);
    }
}
