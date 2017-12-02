package liquibase.change.ext;

import liquibase.database.Database;
import liquibase.statement.SqlStatement;


/**
 * Base class for changes that result in a single OrientDB SQL statement.
 */
public abstract class AbstractSingleStatementOrientChange extends AbstractOrientChange {

    @Override
    public final SqlStatement[] generateStatements(Database database) {
        return new SqlStatement[] { doGenerateStatement(database) };
    }


    protected abstract SqlStatement doGenerateStatement(Database database);
}
