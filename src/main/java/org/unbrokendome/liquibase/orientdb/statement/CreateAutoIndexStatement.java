package org.unbrokendome.liquibase.orientdb.statement;

import java.util.List;

import liquibase.statement.AbstractSqlStatement;
import liquibase.structure.core.Index;


public class CreateAutoIndexStatement extends AbstractCreateIndexStatement<CreateAutoIndexStatement> {

    private String className;
    private List<IndexedProperty> indexedProperties;


    public String getClassName() {
        return className;
    }


    public CreateAutoIndexStatement setClassName(String className) {
        this.className = className;
        return this;
    }


    public List<IndexedProperty> getIndexedProperties() {
        return indexedProperties;
    }


    public CreateAutoIndexStatement setIndexedProperties(List<IndexedProperty> indexedProperties) {
        this.indexedProperties = indexedProperties;
        return this;
    }


    public static class IndexedProperty {

        private final String name;
        private final String by;


        public IndexedProperty(String name, String by) {
            this.name = name;
            this.by = by;
        }


        public IndexedProperty(String name) {
            this(name, null);
        }


        public String getName() {
            return name;
        }


        public String getBy() {
            return by;
        }
    }
}
