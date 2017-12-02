package org.unbrokendome.liquibase.serialization;

import liquibase.serializer.LiquibaseSerializable;

import javax.validation.Path;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;


public class DefaultSerializedFieldInfo implements SerializedFieldInfo {

    private final String name;
    private final String namespace;
    private final String description;
    private final String propertyPath;
    private final Type type;
    private final LiquibaseSerializable.SerializationType serializationType;
    private final Function<Object, Object> getter;
    private final BiConsumer<Object, Object> setter;


    public DefaultSerializedFieldInfo(String name, String namespace, String description, String propertyPath,
                                      Type type, LiquibaseSerializable.SerializationType serializationType,
                                      Function<Object, Object> getter, BiConsumer<Object, Object> setter) {
        this.name = name;
        this.namespace = namespace;
        this.description = description;
        this.propertyPath = propertyPath;
        this.type = type;
        this.serializationType = serializationType;
        this.getter = getter;
        this.setter = setter;
    }


    @Override
    public String getName() {
        return name;
    }


    @Override
    public String getNamespace() {
        return namespace;
    }


    @Override
    public String getDescription() {
        return description;
    }


    @Override
    public String getPropertyPath() {
        return propertyPath;
    }


    @Override
    public Type getType() {
        return type;
    }


    @Override
    public List<Type> getTypeParameters() {
        if (type instanceof ParameterizedType) {
            return Arrays.asList(((ParameterizedType) type).getActualTypeArguments());
        } else {
            return Collections.emptyList();
        }
    }


    @Override
    public LiquibaseSerializable.SerializationType getSerializationType() {
        return serializationType;
    }


    @Override
    public Function<Object, Object> getGetter() {
        return getter;
    }


    @Override
    public BiConsumer<Object, Object> getSetter() {
        return setter;
    }
}
