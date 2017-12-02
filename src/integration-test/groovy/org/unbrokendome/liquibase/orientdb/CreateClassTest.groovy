package org.unbrokendome.liquibase.orientdb

import com.orientechnologies.orient.core.metadata.schema.OType


class CreateClassTest extends AbstractLiquibaseIntegrationTest {
    
    def "create empty class"() {
        when:
            runLiquibase('CreateClass')

        then:
            databaseSchema.getClass('TestClass') != null
    }


    def "create class with properties"() {
        when:
            runLiquibase('CreateClassWithProperties')
        
        then:
            databaseSchema.getClass('TestClass').with {
                it.getProperty('TestProperty1') != null
                it.getProperty('TestProperty1').type == OType.STRING

                it.getProperty('TestProperty2') != null
                it.getProperty('TestProperty2').type == OType.EMBEDDEDLIST
                it.getProperty('TestProperty2').linkedType == OType.INTEGER
            }
    }


    def "create class with superclass"() {
        when:
            runLiquibase('CreateClassWithSuperclass')

        then:
            def baseClass = databaseSchema.getClass('BaseClass')
            def derivedClass = databaseSchema.getClass('DerivedClass')

            baseClass != null
            derivedClass != null
            derivedClass.superClasses == [ baseClass ]
    }


    def "create class with index"() {
        when:
            runLiquibase('CreateClassWithIndex')

        then:
            def testClass = databaseSchema.getClass('TestClass')
            def testProperty = testClass.getProperty('TestProperty')

            testProperty != null
            testClass.indexes.size() == 1
            testClass.indexedProperties.toList() == [testProperty]
    }
}
