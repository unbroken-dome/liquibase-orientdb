package org.unbrokendome.liquibase.orientdb


class MultipleLiquibaseInvocationsTest extends AbstractLiquibaseIntegrationTest {

    def "run Liquibase twice"() {
        when:
            runLiquibase('CreateClass')
            runLiquibase('CreateClass')

        then:
            noExceptionThrown()
    }
}
