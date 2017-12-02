package org.unbrokendome.liquibase.serialization;

import liquibase.serializer.LiquibaseSerializable;

import java.lang.reflect.Type;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;


public class EmbeddedSerializedFieldInfo implements SerializedFieldInfo {

    private final SerializedFieldInfo subFieldInfo;
    private final String propertyPath;
    private final Function<Object, Object> combinedGetter;
    private final BiConsumer<Object, Object> combinedSetter;


    public EmbeddedSerializedFieldInfo(SerializedFieldInfo subFieldInfo, String parentPropertyPath,
                                       Function<Object, Object> parentGetter) {
        this.subFieldInfo = subFieldInfo;

        propertyPath = parentPropertyPath + "." + subFieldInfo.getPropertyPath();

        combinedGetter = parentGetter.andThen(subFieldInfo.getGetter());

        BiConsumer<Object, Object> subFieldSetter = subFieldInfo.getSetter();
        combinedSetter = (obj, value) -> {
            Object fieldValue = parentGetter.apply(obj);
            subFieldSetter.accept(fieldValue, value);
        };
    }


    @Override
    public String getName() {
        return subFieldInfo.getName();
    }


    @Override
    public String getNamespace() {
        return subFieldInfo.getNamespace();
    }


    @Override
    public String getDescription() {
        return subFieldInfo.getDescription();
    }


    @Override
    public String getPropertyPath() {
        return propertyPath;
    }


    @Override
    public Type getType() {
        return subFieldInfo.getType();
    }


    @Override
    public List<Type> getTypeParameters() {
        return subFieldInfo.getTypeParameters();
    }


    @Override
    public LiquibaseSerializable.SerializationType getSerializationType() {
        return subFieldInfo.getSerializationType();
    }


    @Override
    public Function<Object, Object> getGetter() {
        return combinedGetter;
    }


    @Override
    public BiConsumer<Object, Object> getSetter() {
        return combinedSetter;
    }
}
