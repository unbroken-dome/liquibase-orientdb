package liquibase.change.ext;

import liquibase.database.Database;
import liquibase.statement.SqlStatement;

import java.util.stream.Stream;


/**
 * Base class for changes that result in multiple OrientDB SQL statements.
 */
public abstract class AbstractMultiStatementOrientChange extends AbstractOrientChange {

    @Override
    public final SqlStatement[] generateStatements(Database database) {
        return doGenerateStatements(database)
                .toArray(SqlStatement[]::new);
    }


    protected abstract Stream<SqlStatement> doGenerateStatements(Database database);
}
