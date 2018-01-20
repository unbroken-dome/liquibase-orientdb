package liquibase.sqlgenerator.ext;

import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import org.unbrokendome.liquibase.orientdb.common.OrientPropertyType;
import org.unbrokendome.liquibase.orientdb.sql.CreateIndexSql;
import org.unbrokendome.liquibase.orientdb.statement.CreateManualIndexStatement;
import org.unbrokendome.liquibase.orientdb.structure.OrientIndex;
import org.unbrokendome.liquibase.orientdb.structure.OrientIndexType;

import java.util.stream.Collectors;


public class CreateManualIndexSqlGenerator extends AbstractSqlGenerator<CreateManualIndexStatement> {


    @Override
    public ValidationErrors validate(CreateManualIndexStatement statement, Database database,
                                     SqlGeneratorChain sqlGeneratorChain) {

        ValidationErrors validationErrors = new ValidationErrors();

        validationErrors.checkRequiredField("name", statement.getName());
        //validationErrors.checkRequiredField("property", statement.getPropertyNames());
        validationErrors.checkRequiredField("type", statement.getType());

        return validationErrors;
    }


    @Override
    public Sql[] generateSql(CreateManualIndexStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {

        OrientIndex orientIndex = new OrientIndex()
                .setName(statement.getName())
                .setType(OrientIndexType.valueOf(statement.getType().toUpperCase()))
                .setEngine(statement.getEngine())
                .setKeyTypes(
                        statement.getKeyTypes().stream()
                                .map(keyType -> OrientPropertyType.valueOf(keyType.toUpperCase()))
                                .collect(Collectors.toList()))
                .setIgnoreNullValues(statement.isIgnoreNullValues());

        return new Sql[]{new CreateIndexSql(orientIndex)};
    }
}
