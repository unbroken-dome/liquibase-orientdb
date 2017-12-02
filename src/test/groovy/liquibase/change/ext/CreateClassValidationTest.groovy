package liquibase.change.ext

import liquibase.database.Database
import liquibase.database.ext.OrientDatabase
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject


class CreateClassValidationTest extends Specification {

    @Shared
    private Database database = new OrientDatabase()

    @Subject
    def change = new CreateClassChange()


    def "Validation should fail if name is not set"() {
        when:
            def validationErrors = change.validate(database)

        then:
            validationErrors.hasErrors()
            validationErrors.getErrorMessages().any { it.startsWith('name: ') }
    }


    def "Validation should fail if index has neither property nor indexedProperty set"() {
        given:
            change.properties = [ new PropertyConfig(name: 'foo', type: 'string') ]
            change.indexes = [ new IndexConfig(name: 'theIndex') ]

        when:
            def validationErrors = change.validate(database)

        then:
            validationErrors.hasErrors()
            validationErrors.getErrorMessages().any { it.startsWith('index[0]: ') }
    }


    def "Validation should fail if index has both property and indexedProperty set"() {
        given:
            change.properties = [ new PropertyConfig(name: 'foo', type: 'string') ]
            change.indexes = [ new IndexConfig(
                    property: 'foo',
                    indexedProperties: [ new IndexedPropertyConfig(name: 'foo') ]) ]

        when:
            def validationErrors = change.validate(database)

        then:
            validationErrors.hasErrors()
            validationErrors.getErrorMessages().any { it.startsWith('index[0]: ') }
    }


    def "Automatic index must have a name if it indexes multiple properties"() {
        given:
            change.properties = [
                    new PropertyConfig(name: 'foo', type: 'string'),
                    new PropertyConfig(name: 'bar', type: 'string')
            ]
            change.indexes = [ new IndexConfig(property: 'foo bar') ]

        when:
            def validationErrors = change.validate(database)

        then:
            validationErrors.hasErrors()
            validationErrors.getErrorMessages().any { it.startsWith('index[0]: ') }
    }
}
