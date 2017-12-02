package org.unbrokendome.liquibase.orientdb.statement;

import liquibase.statement.AbstractSqlStatement;
import org.unbrokendome.liquibase.orientdb.common.OrientClusterAttribute;

import java.util.LinkedHashMap;
import java.util.Map;


@SuppressWarnings({ "unchecked", "UnusedReturnValue" })
public abstract class AbstractClusterStatement<T extends AbstractClusterStatement<T>>
        extends AbstractSqlStatement {

    private Map<OrientClusterAttribute, Object> attributes = new LinkedHashMap<>();


    public Map<OrientClusterAttribute, Object> getAttributes() {
        return attributes;
    }


    public T setAttribute(OrientClusterAttribute key, Object value) {
        attributes.put(key, value);
        return (T) this;
    }


    public T setAttributes(Map<OrientClusterAttribute, Object> attributes) {
        this.attributes.putAll(attributes);
        return (T) this;
    }
}
