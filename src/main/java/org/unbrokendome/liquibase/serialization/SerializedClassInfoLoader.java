package org.unbrokendome.liquibase.serialization;

import com.google.common.cache.CacheLoader;
import com.google.common.collect.ImmutableList;
import liquibase.util.beans.PropertyUtils;

import javax.annotation.Nonnull;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;


public class SerializedClassInfoLoader extends CacheLoader<Class<?>, SerializedClassInfo> {

    @Override
    public SerializedClassInfo load(@Nonnull Class<?> clazz) throws Exception {
        Serializable annotation = clazz.getAnnotation(Serializable.class);
        if (annotation == null) {
            throw new IllegalArgumentException("Class " + clazz.getName() + " does not have the @Serializable annotation");
        }

        ImmutableList.Builder<SerializedFieldInfo> fieldInfos = ImmutableList.builder();

        for (PropertyDescriptor property : PropertyUtils.getInstance().getDescriptors(clazz)) {
            if (property.getReadMethod() == null) {
                continue;
            }

            SerializedField fieldAnnotation = property.getReadMethod().getAnnotation(SerializedField.class);
            if (fieldAnnotation != null) {

                Method readMethod = property.getReadMethod();
                Method writeMethod = property.getWriteMethod();

                Function<Object, Object> fieldGetter = wrapUnchecked(obj -> readMethod.invoke(obj));
                BiConsumer<Object, Object> fieldSetter = wrapUnchecked((ReflectiveSetter) writeMethod::invoke);

                if (fieldAnnotation.embedded()) {
                    List<SerializedFieldInfo> embeddedFields = getEmbeddedFields(property, fieldGetter, fieldSetter);
                    fieldInfos.addAll(embeddedFields);

                } else {
                    String fieldName = fieldAnnotation.name();
                    if (fieldName.isEmpty()) {
                        fieldName = property.getName();
                    }

                    String fieldNamespace = fieldAnnotation.namespace();
                    if (fieldNamespace.isEmpty()) {
                        fieldNamespace = annotation.namespace();
                    }

                    SerializedFieldInfo fieldInfo = new DefaultSerializedFieldInfo(
                            fieldName, fieldNamespace, fieldAnnotation.description(),
                            property.getName(), property.getReadMethod().getGenericReturnType(),
                            fieldAnnotation.type(),
                            fieldGetter, fieldSetter);
                    fieldInfos.add(fieldInfo);
                }
            }
        }

        return new SerializedClassInfo(annotation.name(), annotation.namespace(),
                annotation.description(), fieldInfos.build());
    }


    private List<SerializedFieldInfo> getEmbeddedFields(PropertyDescriptor property,
                                                        Function<Object, Object> fieldGetter,
                                                        BiConsumer<Object, Object> fieldSetter) throws Exception {

        ImmutableList.Builder<SerializedFieldInfo> subFieldInfos = ImmutableList.builder();

        SerializedClassInfo fieldClassInfo = load(property.getPropertyType());
        for (SerializedFieldInfo subFieldInfo : fieldClassInfo.getFields()) {

            SerializedFieldInfo fieldInfo = new EmbeddedSerializedFieldInfo(subFieldInfo, property.getName(), fieldGetter);
            subFieldInfos.add(fieldInfo);
        }

        return subFieldInfos.build();
    }


    private static Function<Object, Object> wrapUnchecked(ReflectiveGetter getter) {
        return (obj) -> {
            try {
                return getter.getValue(obj);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeReflectiveOperationException(e);
            }
        };
    }


    private static BiConsumer<Object, Object> wrapUnchecked(ReflectiveSetter setter) {
        return (obj, value) -> {
            try {
                setter.setValue(obj, value);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeReflectiveOperationException(e);
            }
        };
    }


    @FunctionalInterface
    private interface ReflectiveGetter {

        Object getValue(Object obj) throws ReflectiveOperationException;
    }


    @FunctionalInterface
    private interface ReflectiveSetter {

        void setValue(Object obj, Object value) throws ReflectiveOperationException;
    }
}
