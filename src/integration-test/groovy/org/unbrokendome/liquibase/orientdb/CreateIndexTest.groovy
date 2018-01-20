package org.unbrokendome.liquibase.orientdb

import com.orientechnologies.orient.core.metadata.schema.OType

class CreateIndexTest extends AbstractLiquibaseIntegrationTest {

    def "create manual index"() {
        when:
            runLiquibase('CreateManualIndex')

        then:
            def index = databaseSession.metadata.indexManager.getIndex("mostRecentRecords")
            index != null
            !index.automatic
            index.type == "UNIQUE"
            index.keyTypes.toList() == [ OType.DATE ]
    }

    def "create auto index"() {
        when:
            runLiquibase('CreateAutoIndex')

        then:
            def bookClass = databaseSchema.getClass("Book")
            bookClass.classIndexes.size() == 1
            def index = bookClass.getClassIndexes().first()
            index.automatic
            index.type == "NOTUNIQUE"
            index.definition.fields == [ "title" ]
    }

    def "create auto index with custom name"() {
        when:
            runLiquibase('CreateAutoIndexWithCustomName')

        then:
            def bookClass = databaseSchema.getClass("Book")
            bookClass.classIndexes.size() == 1
            def index = bookClass.getClassIndexes().first()
            index.name == "books"
    }

    def "create auto index on multiple properties"() {
        when:
            runLiquibase('CreateAutoIndexOnMultipleProperties')

        then:
            def bookClass = databaseSchema.getClass("Book")
            bookClass.classIndexes.size() == 1
            def index = bookClass.getClassIndexes().first()
            index.automatic
            index.type == "UNIQUE"
            index.definition.fields == [ "author", "title" ]
    }

    def "create auto index on an embeddedmap property"() {
        when:
            runLiquibase('CreateAutoIndexOnMapProperty')

        then:
            def bookClass = databaseSchema.getClass("Book")
            bookClass.classIndexes.size() == 1
            def index = bookClass.getClassIndexes().first()
            index.automatic
            index.type == "NOTUNIQUE"
            index.definition.fieldsToIndex == [ "titles by value" ]
    }

    def "create fulltext auto index with engine"() {
        when:
            runLiquibase('CreateAutoIndexFulltext')

        then:
            def bookClass = databaseSchema.getClass("Book")
            bookClass.classIndexes.size() == 1
            def index = bookClass.getClassIndexes().first()
            index.automatic
            index.type == "FULLTEXT"
            index.definition.fields == [ "title" ]
    }
}
