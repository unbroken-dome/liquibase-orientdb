package org.unbrokendome.liquibase.orientdb

import com.orientechnologies.orient.core.conflict.OAutoMergeRecordConflictStrategy
import com.orientechnologies.orient.core.db.ODatabaseDocumentInternal


class CreateClusterTest extends AbstractLiquibaseIntegrationTest {
    
    def "create cluster"() {
        when:
            runLiquibase('CreateCluster')

        then:
            databaseSession.existsCluster('TestCluster')
    }


    def "create cluster with ID"() {
        when:
            runLiquibase('CreateClusterWithId')

        then:
            databaseSession.existsCluster('TestCluster')
        and:
            databaseSession.getClusterIdByName('TestCluster') == 42
    }


    def "create cluster with attributes"() {
        when:
            runLiquibase('CreateClusterWithAttributes')

        then:
            def testCluster = (databaseSession as ODatabaseDocumentInternal).storage.getClusterByName('TestCluster')
            testCluster != null
        and:
            testCluster.recordGrowFactor() == 42
        and:
            testCluster.recordConflictStrategy instanceof OAutoMergeRecordConflictStrategy
    }
}
