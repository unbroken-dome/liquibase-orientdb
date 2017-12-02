package org.unbrokendome.liquibase.orientdb

import com.orientechnologies.orient.core.metadata.schema.OType


class CreatePropertyTest extends AbstractLiquibaseIntegrationTest {

    def "create property"() {
        when:
            runLiquibase('CreateProperty')

        then:
            def testClass = databaseSchema.getClass('TestClass')
            testClass != null

        and:
            def testProperty = testClass.getProperty('TestProperty')
            testProperty != null
            testProperty.type == OType.STRING
    }
}
