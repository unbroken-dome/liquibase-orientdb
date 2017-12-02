package org.unbrokendome.liquibase.orientdb.structure;

import org.unbrokendome.liquibase.orientdb.common.OrientIndexedBy;


public class OrientIndexedProperty {

    private OrientProperty property;
    private OrientIndexedBy by;


    public OrientProperty getProperty() {
        return property;
    }


    public OrientIndexedProperty setProperty(OrientProperty property) {
        this.property = property;
        return this;
    }


    public OrientIndexedBy getBy() {
        return by;
    }


    public OrientIndexedProperty setBy(OrientIndexedBy by) {
        this.by = by;
        return this;
    }
}
