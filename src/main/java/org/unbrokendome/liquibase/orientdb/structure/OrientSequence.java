package org.unbrokendome.liquibase.orientdb.structure;

import org.unbrokendome.liquibase.orientdb.common.OrientSequenceType;


public class OrientSequence extends AbstractOrientDatabaseObject<OrientSequence> {

    private OrientSequenceType type = OrientSequenceType.CACHED;
    private Long start;
    private Integer increment;
    private Integer cacheSize;


    public OrientSequenceType getType() {
        return type;
    }


    public OrientSequence setType(OrientSequenceType type) {
        this.type = type;
        return this;
    }


    public Long getStart() {
        return start;
    }


    public OrientSequence setStart(Long start) {
        this.start = start;
        return this;
    }


    public Integer getIncrement() {
        return increment;
    }


    public OrientSequence setIncrement(Integer increment) {
        this.increment = increment;
        return this;
    }


    public Integer getCacheSize() {
        return cacheSize;
    }


    public OrientSequence setCacheSize(Integer cacheSize) {
        this.cacheSize = cacheSize;
        return this;
    }
}
