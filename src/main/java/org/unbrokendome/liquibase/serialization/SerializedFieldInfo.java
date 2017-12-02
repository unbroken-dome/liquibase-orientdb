package org.unbrokendome.liquibase.serialization;

import liquibase.serializer.LiquibaseSerializable;

import java.lang.reflect.*;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;


public interface SerializedFieldInfo {

    String getName();

    String getNamespace();

    String getDescription();

    String getPropertyPath();

    Type getType();

    List<Type> getTypeParameters();

    LiquibaseSerializable.SerializationType getSerializationType();

    Function<Object, Object> getGetter();

    BiConsumer<Object, Object> getSetter();

    default Class<?> getRawType() {
        return getRawType(getType());
    }

    static Class<?> getRawType(Type type) {
        if (type instanceof Class) {
            return (Class) type;
        } else if (type instanceof ParameterizedType) {
            return getRawType(((ParameterizedType) type).getRawType());
        } else if (type instanceof TypeVariable) {
            return getRawType(((TypeVariable) type).getBounds()[0]);
        } else if (type instanceof GenericArrayType) {
            Class<?> elementType = getRawType(((GenericArrayType) type).getGenericComponentType());
            return Array.newInstance(elementType, 0).getClass();
        } else {
            throw new IllegalArgumentException("Cannot resolve type " + type);
        }
    }

    default boolean isCollection() {
        return Collection.class.isAssignableFrom(getRawType());
    }

    default Class<?> getCollectionElementType() {
        if (isCollection()) {
            List<Type> typeParameters = getTypeParameters();
            if (typeParameters != null && typeParameters.size() == 1) {
                return getRawType(typeParameters.get(0));
            }
        }
        return null;
    }


    default Object getValue(Object obj) {
        return getGetter().apply(obj);
    }


    default void setValue(Object obj, Object value) {
        getSetter().accept(obj, value);
    }
}
