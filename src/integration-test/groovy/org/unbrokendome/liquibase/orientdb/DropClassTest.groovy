package org.unbrokendome.liquibase.orientdb


class DropClassTest extends AbstractLiquibaseIntegrationTest {
    
    def "drop class"() {
        when:
            runLiquibase('DropClass')

        then:
            !databaseSchema.existsClass('TestClass')
    }
}
