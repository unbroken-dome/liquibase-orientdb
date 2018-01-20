package org.unbrokendome.liquibase.orientdb.sql;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import liquibase.structure.DatabaseObject;

import org.apache.commons.lang3.StringUtils;

import org.unbrokendome.liquibase.orientdb.structure.OrientIndex;
import org.unbrokendome.liquibase.orientdb.structure.OrientIndexedProperty;


public class CreateIndexSql extends AbstractOrientSql {

    private final OrientIndex index;


    public CreateIndexSql(OrientIndex index) {
        this.index = index;
    }


    @Override
    public String toSql() {
        StringBuilder builder = new StringBuilder()
                .append("CREATE INDEX ")
                .append(index.getName())
                .append(" ");

        if (index.getOrientClass() != null && index.hasCustomName()) {
            builder.append("ON ")
                    .append(index.getOrientClass().getName())
                    .append(" (")
                    .append(index.getIndexedProperties().stream()
                            .map(this::indexedPropertyToSql)
                            .collect(Collectors.joining(",")))
                    .append(") ");
        }

        builder.append(index.getType());

        if (index.getEngine() != null) {
            builder.append(" ENGINE ").append(index.getEngine());
        }

        if (index.getKeyTypes() != null) {
            builder.append(" ")
                    .append(StringUtils.join(index.getKeyTypes(), ','));
        }

        String metadataString = index.getMetadata();
        if (metadataString != null && !index.isIgnoreNullValues()) {

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                @SuppressWarnings("unchecked")
                Map<String, Object> metadataMap = (Map<String, Object>) objectMapper.readValue(metadataString, Map.class);

                metadataMap.put("ignoreNullValues", false);

                metadataString = objectMapper.writeValueAsString(metadataMap);

            } catch (IOException ex) {
                // This shouldn't happen because we validated the metadata JSON before
                throw new IllegalArgumentException("Invalid JSON metadata in index");
            }

        } else if (!index.isIgnoreNullValues()) {
            metadataString = "{ \"ignoreNullValues\": false }";
        }

        if (metadataString != null) {
            builder.append(" METADATA ").append(metadataString);
        }

        return builder.toString();
    }


    private String indexedPropertyToSql(OrientIndexedProperty indexedProperty) {
        String propertyName = indexedProperty.getProperty().getName();
        if (indexedProperty.getBy() != null) {
            return propertyName + " BY " + indexedProperty.getBy();
        } else {
            return propertyName;
        }
    }


    @Override
    public Collection<? extends DatabaseObject> getAffectedDatabaseObjects() {
        return Collections.singleton(index);
    }
}
