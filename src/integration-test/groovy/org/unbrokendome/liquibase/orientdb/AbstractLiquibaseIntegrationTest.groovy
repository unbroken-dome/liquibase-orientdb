package org.unbrokendome.liquibase.orientdb

import com.orientechnologies.orient.core.db.ODatabaseRecordThreadLocal
import com.orientechnologies.orient.core.db.ODatabaseSession
import com.orientechnologies.orient.core.metadata.schema.OSchema
import liquibase.Contexts
import liquibase.Liquibase
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.ClassLoaderResourceAccessor
import org.junit.After
import org.junit.BeforeClass
import org.junit.Rule
import org.slf4j.bridge.SLF4JBridgeHandler
import spock.lang.Specification

import java.sql.Connection


abstract class AbstractLiquibaseIntegrationTest extends Specification {

    @Rule
    InMemoryDatabase inMemoryDatabase = new InMemoryDatabase(getClass().getName())


    @BeforeClass
    static void installSlf4jBridge() {
        SLF4JBridgeHandler.removeHandlersForRootLogger()
        SLF4JBridgeHandler.install()
    }


    @After
    void cleanupDatabaseThreadLocal() {
        ODatabaseRecordThreadLocal.INSTANCE.remove()
    }


    protected final ODatabaseSession getDatabaseSession() {
        inMemoryDatabase.database
    }


    protected final OSchema getDatabaseSchema() {
        databaseSession.metadata.schema
    }


    protected final void runLiquibase(String changeLogName) throws Exception {

        String fileName = this.class.package.name.replaceAll('\\.', '/') + "/${changeLogName}.xml"

        Connection connection = inMemoryDatabase.jdbcConnection
        try {
            def jdbcConnection = new JdbcConnection(connection)

            Liquibase liquibase = new Liquibase(fileName, new ClassLoaderResourceAccessor(),
                    DatabaseFactory.instance.findCorrectDatabaseImplementation(jdbcConnection))
            liquibase.update((Contexts) null)
        }
        finally {
            connection.close()
        }

        databaseSession.with {
            activateOnCurrentThread()
            reload()
            metadata.reload()
            metadata.schema.reload()
        }
    }
}
