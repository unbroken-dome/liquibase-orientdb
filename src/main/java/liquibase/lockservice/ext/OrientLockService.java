package liquibase.lockservice.ext;

import liquibase.database.Database;
import liquibase.database.ext.OrientDatabase;
import liquibase.exception.LiquibaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.executor.Executor;
import liquibase.executor.ExecutorService;
import liquibase.lockservice.StandardLockService;
import liquibase.statement.core.RawSqlStatement;


public class OrientLockService extends StandardLockService {

    private boolean databaseChangeLogLockTableInitialized = false;

    @Override
    public boolean supports(Database database) {
        return database instanceof OrientDatabase;
    }


    @Override
    public int getPriority() {
        // make sure this LockService is chosen over StandardLockService for OrientDB databases
        return super.getPriority() + 1;
    }


    public boolean isDatabaseChangeLogLockTableInitialized(boolean tableJustCreated) {
        if (!databaseChangeLogLockTableInitialized) {
            Executor executor = ExecutorService.getInstance().getExecutor(database);

            try {
                databaseChangeLogLockTableInitialized = executor.queryForLong(new RawSqlStatement("select count(*) from " + database.escapeTableName(database.getLiquibaseCatalogName(), database.getLiquibaseSchemaName(), database.getDatabaseChangeLogLockTableName()))) > 0;
            } catch (LiquibaseException e) {
                if (executor.updatesDatabase()) {
                    throw new UnexpectedLiquibaseException(e);
                } else {
                    //probably didn't actually create the table yet.
                    databaseChangeLogLockTableInitialized = !tableJustCreated;
                }
            }
        }
        return databaseChangeLogLockTableInitialized;
    }


    @Override
    public void reset() {
        super.reset();
        databaseChangeLogLockTableInitialized = false;
    }
}
