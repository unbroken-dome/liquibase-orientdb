package org.unbrokendome.liquibase.orientdb

import com.orientechnologies.orient.core.metadata.schema.OType


class CreateEdgeClassTest extends AbstractLiquibaseIntegrationTest {
    
    def "create edge class"() {
        when:
            runLiquibase('CreateEdgeClass')

        then: "WrittenBy class should exist"
            def writtenByClass = databaseSchema.getClass('WrittenBy')
            writtenByClass != null

        and: "WrittenBy should extend from E"
            writtenByClass.superClasses == [ databaseSchema.getClass('E') ]
    }


    def "create edge class with link properties"() {
        when:
            runLiquibase('CreateEdgeClassWithLinkProperties')

        then: "WrittenBy class should exist"
            def writtenByClass = databaseSchema.getClass('WrittenBy')
            writtenByClass != null

        and: "WrittenBy.out should link to Book"
            def outProperty = writtenByClass.getProperty('out')
            outProperty != null
            outProperty.type == OType.LINK
            outProperty.linkedClass == databaseSchema.getClass('Book')

        and: "WrittenBy.in should link to Author"
            def inProperty = writtenByClass.getProperty('in')
            inProperty != null
            inProperty.type == OType.LINK
            inProperty.linkedClass == databaseSchema.getClass('Author')
    }


    def "create edge class with vertex-link properties"() {
        when:
            runLiquibase('CreateEdgeClassWithVertexLinkProperties')

        then: "WrittenBy class should exist"
            def writtenByClass = databaseSchema.getClass('WrittenBy')
            writtenByClass != null

        and: "Book.out_WrittenBy should link to WrittenBy"
            def bookClass = databaseSchema.getClass('Book')
            def bookOutWrittenByProperty = bookClass.getProperty('out_WrittenBy')
            bookOutWrittenByProperty != null
            bookOutWrittenByProperty.type == OType.LINKLIST
            bookOutWrittenByProperty.linkedClass == writtenByClass

        and: "Author.in_WrittenBy should link to WrittenBy"
            def authorClass = databaseSchema.getClass('Author')
            def authorInWrittenByProperty = authorClass.getProperty('in_WrittenBy')
            authorInWrittenByProperty != null
            authorInWrittenByProperty.type == OType.LINKLIST
            authorInWrittenByProperty.linkedClass == writtenByClass
    }
}
