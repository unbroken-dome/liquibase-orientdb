package liquibase.sqlgenerator.ext;

import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import org.unbrokendome.liquibase.orientdb.sql.DropIndexSql;
import org.unbrokendome.liquibase.orientdb.statement.DropIndexStatement;
import org.unbrokendome.liquibase.orientdb.structure.OrientIndex;


public class DropIndexSqlGenerator extends AbstractSqlGenerator<DropIndexStatement> {

    @Override
    public ValidationErrors validate(DropIndexStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("indexName", statement.getIndexName());
        return validationErrors;
    }


    @Override
    public Sql[] generateSql(DropIndexStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {

        OrientIndex index = new OrientIndex()
                .setName(statement.getIndexName());

        return new Sql[] { new DropIndexSql(index) };
    }
}
