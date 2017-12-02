package liquibase.sqlgenerator.ext;

import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import liquibase.statement.core.CreateSequenceStatement;
import org.unbrokendome.liquibase.orientdb.sql.CreateSequenceSql;
import org.unbrokendome.liquibase.orientdb.structure.OrientSequence;

import java.math.BigInteger;


public class OrientCreateSequenceSqlGenerator extends AbstractSqlGenerator<CreateSequenceStatement> {

    @Override
    public int getPriority() {
        return PRIORITY_DATABASE;
    }


    @Override
    public ValidationErrors validate(CreateSequenceStatement statement, Database database,
                                     SqlGeneratorChain sqlGeneratorChain) {
        return new ValidationErrors();
    }


    @Override
    public Sql[] generateSql(CreateSequenceStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {

        OrientSequence sequence = new OrientSequence()
                .setName(statement.getSequenceName())
                .setStart(safeLongValue(statement.getStartValue()))
                .setIncrement(safeIntValue(statement.getIncrementBy()))
                .setCacheSize(safeIntValue(statement.getCacheSize()));

        return new Sql[] { new CreateSequenceSql(sequence) };
    }


    private static Long safeLongValue(BigInteger value) {
        return value != null ? value.longValue() : null;
    }


    private static Integer safeIntValue(BigInteger value) {
        return value != null ? value.intValue() : null;
    }
}
