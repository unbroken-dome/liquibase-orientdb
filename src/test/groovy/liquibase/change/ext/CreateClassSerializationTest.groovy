package liquibase.change.ext

import liquibase.parser.core.ParsedNode
import liquibase.resource.ResourceAccessor
import liquibase.sdk.resource.MockResourceAccessor
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject


class CreateClassSerializationTest extends Specification {

    public static final String NAMESPACE = 'http://www.unbroken-dome.org/schema/liquibase-orientdb'

    @Subject
    def change = new CreateClassChange()

    @Shared
    private ResourceAccessor resourceAccessor = new MockResourceAccessor()


    def "Should load from empty node"() {
        given:
            def parsedNode = new ParsedNode(NAMESPACE, 'createClass')
        when:
            change.load(parsedNode, resourceAccessor)
        then:
            noExceptionThrown()
    }


    def "Should load name from child node"() {
        given:
            def parsedNode = new ParsedNode(NAMESPACE, 'createClass')
                    .addChild(NAMESPACE, 'name', 'theName')
        when:
            change.load(parsedNode, resourceAccessor)
        then:
            change.name == 'theName'
    }


    def "Should load isAbstract from child node"() {
        given:
            def parsedNode = new ParsedNode(NAMESPACE, 'createClass')
                    .addChild(NAMESPACE, 'abstract', 'true')
        when:
            change.load(parsedNode, resourceAccessor)
        then:
            change.abstract
    }
}
