package org.unbrokendome.liquibase.orientdb.common;


public enum OrientPropertyType {

    BOOLEAN,
    INTEGER,
    SHORT,
    LONG,
    FLOAT,
    DOUBLE,
    DATE,
    DATETIME,
    STRING,
    BINARY,
    EMBEDDED,
    EMBEDDEDLIST,
    EMBEDDEDSET,
    EMBEDDEDMAP,
    LINK,
    LINKLIST,
    LINKSET,
    LINKMAP,
    DECIMAL,
    BYTE;


    public boolean isLink() {
        return this == LINK || this == LINKLIST || this == LINKSET || this == LINKMAP;
    }


    public boolean isEmbedded() {
        return this == EMBEDDED || this == EMBEDDEDLIST || this == EMBEDDEDSET || this == EMBEDDEDMAP;
    }


    public static OrientPropertyType parse(String value) {
        if (value == null) {
            throw new IllegalArgumentException("value is null");
        }
        return valueOf(value.toUpperCase());
    }


    public static OrientPropertyType tryParse(String value) {
        if (value != null) {
            try {
                return valueOf(value.toUpperCase());
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        return null;
    }
}
