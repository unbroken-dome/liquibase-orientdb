package org.unbrokendome.liquibase.orientdb.sql;

import liquibase.structure.DatabaseObject;
import org.unbrokendome.liquibase.orientdb.structure.OrientSequence;

import java.util.Collection;
import java.util.Collections;


public class CreateSequenceSql extends AbstractOrientSql {

    private final OrientSequence sequence;


    public CreateSequenceSql(OrientSequence sequence) {
        this.sequence = sequence;
    }


    @Override
    public String toSql() {
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE SEQUENCE ").append(sequence.getName());

        if (sequence.getType() != null) {
            builder.append(" TYPE ").append(sequence.getType());
        }

        if (sequence.getStart() != null) {
            builder.append(" START ").append(sequence.getStart());
        }

        if (sequence.getIncrement() != null) {
            builder.append(" INCREMENT ").append(sequence.getIncrement());
        }

        if (sequence.getCacheSize() != null) {
            builder.append(" CACHE ").append(sequence.getCacheSize());
        }

        return builder.toString();
    }


    @Override
    public Collection<? extends DatabaseObject> getAffectedDatabaseObjects() {
        return Collections.singleton(sequence);
    }
}
