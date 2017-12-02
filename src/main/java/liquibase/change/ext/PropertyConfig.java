package liquibase.change.ext;

import liquibase.statement.SqlStatement;
import org.unbrokendome.liquibase.orientdb.common.OrientPropertyAttribute;
import org.unbrokendome.liquibase.orientdb.common.OrientPropertyType;
import org.unbrokendome.liquibase.orientdb.statement.CreatePropertyStatement;
import org.unbrokendome.liquibase.orientdb.statement.SetPropertyAttributeStatement;
import org.unbrokendome.liquibase.serialization.AnnotatedSerializable;
import org.unbrokendome.liquibase.serialization.Serializable;
import org.unbrokendome.liquibase.serialization.SerializedField;
import org.unbrokendome.liquibase.validation.OneOfEnum;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.stream.Stream;


@Serializable(name = "property", namespace = OrientSerializationConstants.ORIENT_CHANGELOG_NAMESPACE)
public class PropertyConfig implements AnnotatedSerializable {

	private String name;
	private String type;
	private String linkedType;
	private Boolean notNull;
	private Boolean mandatory;
	private String collate;
	private Boolean readOnly;
	private String defaultValue;


	@SerializedField
	@NotNull
	@Size(min = 1)
	public String getName() {
		return name;
	}


	public PropertyConfig setName(String name) {
		this.name = name;
		return this;
	}


	@SerializedField
	@NotNull
	@OneOfEnum(value = OrientPropertyType.class, ignoreCase = true)
	public String getType() {
		return type;
	}


	public PropertyConfig setType(String type) {
		this.type = type;
		return this;
	}


	@SerializedField
	public String getLinkedType() {
		return linkedType;
	}


	public PropertyConfig setLinkedType(String linkedType) {
		this.linkedType = linkedType;
		return this;
	}


	@SerializedField
	public Boolean getNotNull() {
		return notNull;
	}


	public void setNotNull(Boolean notNull) {
		this.notNull = notNull;
	}


	@SerializedField
	public Boolean getMandatory() {
		return mandatory;
	}


	public void setMandatory(Boolean mandatory) {
		this.mandatory = mandatory;
	}


	@SerializedField
	public String getCollate() {
		return collate;
	}


	public void setCollate(String collate) {
		this.collate = collate;
	}


	@SerializedField
    public Boolean getReadOnly() {
        return readOnly;
    }


    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
    }


	@SerializedField(name = "default")
    public String getDefaultValue() {
        return defaultValue;
    }


    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }


	public Stream<SqlStatement> generateStatements(String className) {

        Stream.Builder<SqlStatement> statements = Stream.builder();

		statements.add(buildCreatePropertyStatement(className));

		if (notNull != null) {
			statements.add(buildSetPropertyAttributeStatement(className, OrientPropertyAttribute.NOTNULL, notNull));
		}
		if (mandatory != null) {
			statements.add(buildSetPropertyAttributeStatement(className, OrientPropertyAttribute.MANDATORY, mandatory));
		}
		if (collate != null) {
			statements.add(buildSetPropertyAttributeStatement(className, OrientPropertyAttribute.COLLATE, collate));
		}
        if (readOnly != null) {
            statements.add(buildSetPropertyAttributeStatement(className, OrientPropertyAttribute.READONLY, readOnly));
        }
        if (defaultValue != null) {
            statements.add(buildSetPropertyAttributeStatement(className, OrientPropertyAttribute.DEFAULT, defaultValue));
        }

        return statements.build();
	}


	private CreatePropertyStatement buildCreatePropertyStatement(String className) {
		return new CreatePropertyStatement()
				.setClassName(className)
				.setPropertyName(name)
				.setType(type)
				.setLinkedType(linkedType);
	}


	private SetPropertyAttributeStatement buildSetPropertyAttributeStatement(String className,
			OrientPropertyAttribute attribute, Object attributeValue) {

		return new SetPropertyAttributeStatement()
				.setClassName(className)
				.setPropertyName(name)
				.setAttributeName(attribute)
				.setAttributeValue(attributeValue);
	}
}
