package org.unbrokendome.liquibase.serialization;

import com.google.common.collect.Maps;

import javax.validation.ElementKind;
import javax.validation.Path;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SerializedClassInfo {

    private final String name;
    private final String namespace;
    private final String description;
    private final Map<String, SerializedFieldInfo> fields;
    private final Map<String, SerializedFieldInfo> fieldsByPropertyPath;


    public SerializedClassInfo(String name, String namespace, String description, List<SerializedFieldInfo> fields) {
        this.name = name;
        this.namespace = namespace;
        this.description = description;
        this.fields = Maps.uniqueIndex(fields, SerializedFieldInfo::getName);
        this.fieldsByPropertyPath = Maps.uniqueIndex(fields, SerializedFieldInfo::getPropertyPath);
    }


    public String getName() {
        return name;
    }


    public String getNamespace() {
        return namespace;
    }


    public Map<String, SerializedFieldInfo> getFieldMap() {
        return fields;
    }


    public Set<String> getFieldNames() {
        return fields.keySet();
    }


    public Collection<SerializedFieldInfo> getFields() {
        return fields.values();
    }


    public SerializedFieldInfo getField(String name) {
        return fields.get(name);
    }


    public SerializedFieldInfo getFieldByPropertyPath(String propertyPath) {
        return fieldsByPropertyPath.get(propertyPath);
    }


    public String getDescription() {
        return description;
    }


    String translatePropertyPathToSerializedPath(Path path,
                                                 Function<Class<?>, SerializedClassInfo> classInfoProvider) {
        return translatePropertyPathToSerializedPathInternal(path.iterator(), classInfoProvider);
    }


    private String translatePropertyPathToSerializedPathInternal(Iterator<Path.Node> pathNodes,
                                                                 Function<Class<?>, SerializedClassInfo> classInfoProvider) {

        List<String> pathParts = new ArrayList<>(3);

        while (pathNodes.hasNext()) {
            Path.Node node = pathNodes.next();
            if (node.getKind() == ElementKind.PROPERTY) {

                pathParts.add(node.getName());
                String combinedPath = String.join(".", pathParts);

                SerializedFieldInfo fieldInfo = fieldsByPropertyPath.get(combinedPath);

                if (fieldInfo != null) {
                    String serializedPath;
                    Class<?> fieldType;

                    Matcher matcher = Pattern.compile(".*\\[(.*)\\]$").matcher(node.toString());
                    if (matcher.matches()) {
                        serializedPath = fieldInfo.getName() + "[" + matcher.group(1) + "]";
                        fieldType = fieldInfo.getCollectionElementType();

                    } else {
                        serializedPath = fieldInfo.getName();
                        fieldType = fieldInfo.getRawType();
                    }

                    if (pathNodes.hasNext()) {
                        SerializedClassInfo fieldClassInfo = classInfoProvider.apply(fieldType);
                        if (fieldClassInfo != null) {
                            String subPath = fieldClassInfo.translatePropertyPathToSerializedPathInternal(
                                    pathNodes, classInfoProvider);
                            if (subPath != null) {
                                return serializedPath + "." + subPath;
                            }
                        }
                    }

                    return serializedPath;
                }
            }
        }

        return null;
    }
}
