package org.unbrokendome.liquibase.orientdb.statement;


import liquibase.statement.AbstractSqlStatement;


public abstract class AbstractCreateIndexStatement<T extends AbstractCreateIndexStatement<T>>
        extends AbstractSqlStatement {

    private String name;
    private String type;
    private boolean ignoreNullValues = true;


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


    public boolean isIgnoreNullValues() {
        return ignoreNullValues;
    }


    public T setIgnoreNullValues(boolean ignoreNullValues) {
        this.ignoreNullValues = ignoreNullValues;
        return self();
    }


    @SuppressWarnings("unchecked")
    private T self() {
        return (T) this;
    }
}
