package org.unbrokendome.liquibase.serialization;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.parser.core.ParsedNode;
import liquibase.parser.core.ParsedNodeException;
import liquibase.resource.ResourceAccessor;
import liquibase.serializer.LiquibaseSerializable;
import liquibase.util.ISODateFormat;
import org.apache.commons.beanutils.ConvertUtils;

import javax.validation.ElementKind;
import javax.validation.Path;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AnnotationReflectionSerializer {

    private static final AnnotationReflectionSerializer INSTANCE = new AnnotationReflectionSerializer();

    private LoadingCache<Class<?>, SerializedClassInfo> classInfoCache = CacheBuilder.newBuilder()
            .build(new SerializedClassInfoLoader());


    private AnnotationReflectionSerializer() {
    }


    public static AnnotationReflectionSerializer getInstance() {
        return INSTANCE;
    }


    public SerializedClassInfo getSerializedClassInfo(Object obj) {
        return getClassInfo(obj);
    }


    public Set<String> getSerializableFields(Object obj) {
        return getClassInfo(obj).getFieldNames();
    }


    public Object getSerializableFieldValue(Object obj, String field) {
        return getFieldInfo(obj, field).getGetter().apply(obj);
    }


    public LiquibaseSerializable.SerializationType getSerializableFieldType(Object obj, String field) {
        return getFieldInfo(obj, field).getSerializationType();
    }


    public String getSerializableFieldNamespace(Object obj, String field) {
        return getFieldInfo(obj, field).getNamespace();
    }


    public String getSerializedObjectNamespace(Object obj) {
        return getClassInfo(obj).getNamespace();
    }


    private SerializedClassInfo getClassInfo(Object obj) {
        return classInfoCache.getUnchecked(obj.getClass());
    }


    private SerializedFieldInfo getFieldInfo(Object obj, String fieldName) {
        return getClassInfo(obj).getField(fieldName);
    }


    public String translatePropertyPathToSerializedPath(Object rootObj, Path path) {

        SerializedClassInfo classInfo = getClassInfo(rootObj);
        return classInfo.translatePropertyPathToSerializedPath(path, classInfoCache::getUnchecked);
    }


    public void load(Object obj, ParsedNode parsedNode, ResourceAccessor resourceAccessor) throws ParsedNodeException {

        SerializedClassInfo classInfo = getClassInfo(obj);
        Map<String, SerializedFieldInfo> fieldMap = classInfo.getFieldMap();

        for (ParsedNode childNode : parsedNode.getChildren()) {
            try {
                SerializedFieldInfo field = fieldMap.get(childNode.getName());

                if (field != null) {
                    mapToExistingField(obj, childNode, field, resourceAccessor);

                } else {
                    for (SerializedFieldInfo existingField : fieldMap.values()) {
                        if (isCollectionOfSerializable(existingField)
                                && childNode.getName().equals(getCollectionElementName(existingField))) {
                            List<ParsedNode> elementNodes = Collections.singletonList(childNode);
                            populateCollectionPropertyFromNodes(obj, resourceAccessor, existingField, elementNodes);
                            break;
                        }
                    }
                }

            } catch (Exception e) {
                throw new ParsedNodeException("Error setting property", e);
            }
        }

        if (parsedNode.getValue() != null) {
            for (SerializedFieldInfo field : fieldMap.values()) {
                switch (field.getSerializationType()) {
                    case DIRECT_VALUE: {
                        Object value = parsedNode.getValue(String.class);
                        value = convertEscaped(value);
                        field.setValue(obj, value);
                        break;
                    }
                    case NAMED_FIELD: {
                        Object value = parsedNode.getChildValue(field.getNamespace(), field.getName(), Object.class);
                        value = convertEscaped(value);
                        field.setValue(obj, value);
                        break;
                    }
                }
            }
        }
    }


    private void mapToExistingField(Object obj, ParsedNode childNode, SerializedFieldInfo field,
                                    ResourceAccessor resourceAccessor)
            throws ReflectiveOperationException, ParsedNodeException {

        if (isCollectionOfSerializable(field)) {
            String elementName = getCollectionElementName(field);
            List<ParsedNode> elementNodes;
            if (childNode.getName().equals(elementName)) {
                elementNodes = Collections.singletonList(childNode);
            } else {
                elementNodes = childNode.getChildren(null, elementName);
            }
            populateCollectionPropertyFromNodes(obj, resourceAccessor, field, elementNodes);

        } else {
            Class<?> dataTypeClass = field.getRawType();

            if (LiquibaseSerializable.class.isAssignableFrom(dataTypeClass)) {
                if (!dataTypeClass.isInterface()
                        && !Modifier.isAbstract(dataTypeClass.getModifiers())) {

                    LiquibaseSerializable childObject = (LiquibaseSerializable) dataTypeClass.newInstance();
                    childObject.load(childNode, resourceAccessor);
                    field.setValue(obj, childObject);
                }

            } else if (childNode.getValue() != null) {
                Object value = convertEscaped(childNode.getValue());
                value = ConvertUtils.convert(value, field.getRawType());
                field.setValue(obj, value);
            }
        }
    }


    private boolean isCollectionOfSerializable(SerializedFieldInfo field) {

        if (field.isCollection()) {
            Class<?> elementType = field.getCollectionElementType();
            return elementType != null
                    && LiquibaseSerializable.class.isAssignableFrom(elementType)
                    && !elementType.isInterface()
                    && !Modifier.isAbstract(elementType.getModifiers());
        }
        return false;
    }


    private String getCollectionElementName(SerializedFieldInfo field) {

        Class<?> elementType = field.getCollectionElementType();

        if (AnnotatedSerializable.class.isAssignableFrom(elementType)) {
            SerializedClassInfo classInfo = classInfoCache.getUnchecked(elementType);
            return classInfo.getName();
        }

        try {
            LiquibaseSerializable serializable = elementType.asSubclass(LiquibaseSerializable.class).newInstance();
            return serializable.getSerializedObjectName();

        } catch (ReflectiveOperationException e) {
            return null;
        }
    }


    @SuppressWarnings("unchecked")
    private void populateCollectionPropertyFromNodes(Object obj, ResourceAccessor resourceAccessor,
                                                     SerializedFieldInfo field, List<ParsedNode> elementNodes)
            throws ReflectiveOperationException, ParsedNodeException {

        if (!elementNodes.isEmpty()) {
            Collection<LiquibaseSerializable> collection = (Collection<LiquibaseSerializable>) field.getValue(obj);
            Class<?> elementType = field.getCollectionElementType();
            for (ParsedNode node : elementNodes) {
                LiquibaseSerializable childObject = (LiquibaseSerializable) elementType.newInstance();
                childObject.load(node, resourceAccessor);
                collection.add(childObject);
            }
        }
    }


    private static Object convertEscaped(Object value) {
        if (value == null) {
            return null;
        }
        Matcher matcher = Pattern.compile("(.*)!\\{(.*)\\}").matcher((String) value);
        if (matcher.matches()) {
            String stringValue = matcher.group(1);
            try {
                Class<?> aClass = Class.forName(matcher.group(2));
                if (Date.class.isAssignableFrom(aClass)) {
                    Date date = new ISODateFormat().parse(stringValue);
                    value = aClass.getConstructor(long.class).newInstance(date.getTime());

                } else if (Enum.class.isAssignableFrom(aClass)) {
                    value = Enum.valueOf(aClass.asSubclass(Enum.class), stringValue);

                } else {
                    value = aClass.getConstructor(String.class).newInstance(stringValue);
                }

            } catch (Exception e) {
                throw new UnexpectedLiquibaseException(e);
            }
        }
        return value;
    }


    public ParsedNode serialize(Object obj) throws ParsedNodeException {

        SerializedClassInfo classInfo = getClassInfo(obj);

        ParsedNode node = new ParsedNode(null, classInfo.getName());
        for (SerializedFieldInfo fieldInfo : classInfo.getFields()) {
            Object fieldValue = fieldInfo.getValue(obj);
            fieldValue = serializeValue(fieldValue);
            if (fieldValue == null) {
                continue;
            }

            switch (fieldInfo.getSerializationType()) {
                case DIRECT_VALUE:
                    node.setValue(fieldValue);
                    break;

                case NAMED_FIELD:
                case NESTED_OBJECT:
                    if (fieldValue instanceof ParsedNode) {
                        node.addChild((ParsedNode) fieldValue);
                    } else {
                        node.addChild(new ParsedNode(fieldInfo.getNamespace(), fieldInfo.getName()).setValue(fieldValue));
                    }

                default:
                    throw new UnexpectedLiquibaseException("Unknown serialization type: "
                            + fieldInfo.getSerializationType());
            }
        }
        return node;
    }


    private Object serializeValue(Object value) throws ParsedNodeException {
        if (value instanceof Collection) {
            List<Object> returnList = new ArrayList<>();
            for (Object obj : (Collection) value) {
                Object objValue = serializeValue(obj);
                if (objValue != null) {
                    returnList.add(objValue);
                }
            }
            if (((Collection) value).isEmpty()) {
                return null;
            } else {
                return returnList;
            }

        } else if (value instanceof LiquibaseSerializable) {
            return ((LiquibaseSerializable) value).serialize();

        } else {
            return value;
        }
    }
}
