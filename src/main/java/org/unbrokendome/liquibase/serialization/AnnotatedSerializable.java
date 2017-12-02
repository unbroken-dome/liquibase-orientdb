package org.unbrokendome.liquibase.serialization;

import liquibase.parser.core.ParsedNode;
import liquibase.parser.core.ParsedNodeException;
import liquibase.resource.ResourceAccessor;
import liquibase.serializer.AbstractLiquibaseSerializable;
import liquibase.serializer.LiquibaseSerializable;

import java.lang.reflect.Type;
import java.util.Set;


public interface AnnotatedSerializable extends LiquibaseSerializable {

    @Override
    default String getSerializedObjectName() {
        Serializable annotation = this.getClass().getAnnotation(Serializable.class);
        if (annotation != null && !annotation.name().isEmpty()) {
            return annotation.name();
        } else {
            return this.getClass().getSimpleName();
        }
    }


    @Override
    default Set<String> getSerializableFields() {
        return AnnotationReflectionSerializer.getInstance().getSerializableFields(this);
    }


    @Override
    default Object getSerializableFieldValue(String field) {
        return AnnotationReflectionSerializer.getInstance().getSerializableFieldValue(this, field);
    }


    @Override
    default SerializationType getSerializableFieldType(String field) {
        return AnnotationReflectionSerializer.getInstance().getSerializableFieldType(this, field);
    }


    @Override
    default String getSerializableFieldNamespace(String field) {
        return AnnotationReflectionSerializer.getInstance().getSerializableFieldNamespace(this, field);
    }


    @Override
    default String getSerializedObjectNamespace() {
        return AnnotationReflectionSerializer.getInstance().getSerializedObjectNamespace(this);
    }


    @Override
    default void load(ParsedNode parsedNode, ResourceAccessor resourceAccessor) throws ParsedNodeException {
        AnnotationReflectionSerializer.getInstance().load(this, parsedNode, resourceAccessor);
    }

    @Override
    default ParsedNode serialize() throws ParsedNodeException {
        return AnnotationReflectionSerializer.getInstance().serialize(this);
    }
}
