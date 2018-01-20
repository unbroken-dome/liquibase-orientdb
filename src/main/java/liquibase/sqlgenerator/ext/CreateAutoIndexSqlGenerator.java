package liquibase.sqlgenerator.ext;

import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import org.unbrokendome.liquibase.orientdb.common.OrientIndexedBy;
import org.unbrokendome.liquibase.orientdb.sql.CreateIndexSql;
import org.unbrokendome.liquibase.orientdb.statement.CreateAutoIndexStatement;
import org.unbrokendome.liquibase.orientdb.structure.*;

import java.util.List;
import java.util.stream.Collectors;


public class CreateAutoIndexSqlGenerator extends AbstractSqlGenerator<CreateAutoIndexStatement> {


    @Override
    public ValidationErrors validate(CreateAutoIndexStatement statement, Database database,
                                     SqlGeneratorChain sqlGeneratorChain) {

        ValidationErrors validationErrors = new ValidationErrors();

        validationErrors.checkRequiredField("className", statement.getClassName());
        //validationErrors.checkRequiredField("property", statement.getPropertyNames());
        validationErrors.checkRequiredField("type", statement.getType());

        return validationErrors;
    }


    @Override
    public Sql[] generateSql(CreateAutoIndexStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {

        OrientClass orientClass = new OrientClass(statement.getClassName());

        List<OrientIndexedProperty> indexedProperties = statement.getIndexedProperties().stream()
                .map(p -> {
                    OrientIndexedProperty indexedProperty = new OrientIndexedProperty()
                            .setProperty(new OrientProperty(orientClass, p.getName()));
                    if (p.getBy() != null) {
                        indexedProperty.setBy(OrientIndexedBy.valueOf(p.getBy().toUpperCase()));
                    }
                    return indexedProperty;
                })
                .collect(Collectors.toList());

        OrientIndex orientIndex = new OrientIndex()
                .setName(statement.getName())
                .setOrientClass(orientClass)
                .setIndexedProperties(indexedProperties)
                .setType(OrientIndexType.valueOf(statement.getType().toUpperCase()))
                .setEngine(statement.getEngine())
                .setIgnoreNullValues(statement.isIgnoreNullValues());

        return new Sql[] { new CreateIndexSql(orientIndex) };
    }
}
