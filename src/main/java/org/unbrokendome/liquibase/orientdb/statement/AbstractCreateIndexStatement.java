package org.unbrokendome.liquibase.orientdb.statement;


import liquibase.statement.AbstractSqlStatement;


public abstract class AbstractCreateIndexStatement<T extends AbstractCreateIndexStatement<T>>
        extends AbstractSqlStatement {

    private String name;
    private String type;
    private String engine;
    private boolean ignoreNullValues = true;
    private String metadata;


    public String getName() {
        return name;
    }


    public T setName(String name) {
        this.name = name;
        return self();
    }


    public String getType() {
        return type;
    }


    public T setType(String type) {
        this.type = type;
        return self();
    }


    public String getEngine() {
        return engine;
    }


    public T setEngine(String engine) {
        this.engine = engine;
        return self();
    }


    public boolean isIgnoreNullValues() {
        return ignoreNullValues;
    }


    public T setIgnoreNullValues(boolean ignoreNullValues) {
        this.ignoreNullValues = ignoreNullValues;
        return self();
    }


    public String getMetadata() {
        return metadata;
    }


    public T setMetadata(String metadata) {
        this.metadata = metadata;
        return self();
    }


    @SuppressWarnings("unchecked")
    private T self() {
        return (T) this;
    }
}
