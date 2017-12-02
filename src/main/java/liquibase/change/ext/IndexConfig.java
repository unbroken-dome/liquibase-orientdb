package liquibase.change.ext;

import liquibase.statement.SqlStatement;
import org.unbrokendome.liquibase.orientdb.statement.CreateAutoIndexStatement;
import org.unbrokendome.liquibase.orientdb.statement.CreateManualIndexStatement;
import org.unbrokendome.liquibase.serialization.AnnotatedSerializable;
import org.unbrokendome.liquibase.serialization.Serializable;
import org.unbrokendome.liquibase.serialization.SerializedField;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Serializable(name = "index", namespace = OrientSerializationConstants.ORIENT_CHANGELOG_NAMESPACE)
public class IndexConfig implements AnnotatedSerializable {

    private String name;
    private List<String> propertyNames;
    private List<IndexedPropertyConfig> indexedProperties = new ArrayList<>();
    private String type;
    private Boolean ignoreNullValues;


    @SerializedField(description = "The name of the index")
    @NotNull(groups = ManualIndex.class)
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    @SerializedField
    public String getProperty() {
        if (propertyNames != null) {
            return String.join(" ", propertyNames);
        } else {
            return null;
        }
    }


    public void setProperty(String property) {
        if (property != null) {
            this.propertyNames = Arrays.asList(property.split("\\s+"));
        } else {
            this.propertyNames = null;
        }
    }


    @SerializedField(name = "indexedProperty")
    public List<IndexedPropertyConfig> getIndexedProperties() {
        return indexedProperties;
    }


    public void setIndexedProperties(List<IndexedPropertyConfig> indexedProperties) {
        this.indexedProperties = indexedProperties;
    }


    @SerializedField
    @NotNull
    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
    }


    @SerializedField
    public Boolean getIgnoreNullValues() {
        return ignoreNullValues;
    }


    public void setIgnoreNullValues(Boolean ignoreNullValues) {
        this.ignoreNullValues = ignoreNullValues;
    }


    private int getNumberOfIndexedProperties() {
        if (propertyNames != null) {
            return propertyNames.size();
        } else {
            return indexedProperties.size();
        }
    }


    @AssertTrue(groups = IndexConfig.AutoIndex.class,
            message = "{liquibase.change.ext.IndexConfig.nameMustBeSetForMultipleProperties}")
    public boolean isNameGivenForMultipleProperties() {
        return name != null || getNumberOfIndexedProperties() < 2;
    }


    @AssertTrue(groups = IndexConfig.AutoIndex.class,
            message = "{liquibase.change.ext.IndexConfig.mustSetEitherPropertyNamesOrIndexedProperties}")
    public boolean isEitherPropertyNamesOrIndexedPropertiesSet() {
        return (propertyNames == null || propertyNames.isEmpty()) != (indexedProperties.isEmpty());
    }


    public SqlStatement generateStatementForManualIndex(List<String> keyTypes) {
        CreateManualIndexStatement statement = new CreateManualIndexStatement()
                .setName(getName())
                .setType(getType())
                .setKeyTypes(keyTypes);

        if (ignoreNullValues != null) {
            statement.setIgnoreNullValues(ignoreNullValues);
        }

        return statement;
    }


    public SqlStatement generateStatementForAutoIndex(String className) {

        List<CreateAutoIndexStatement.IndexedProperty> indexedProperties;

        if (propertyNames != null && !propertyNames.isEmpty()) {
            indexedProperties = propertyNames.stream()
                    .map(CreateAutoIndexStatement.IndexedProperty::new)
                    .collect(Collectors.toList());
        } else if (this.indexedProperties != null && !this.indexedProperties.isEmpty()) {
            indexedProperties =
                    getIndexedProperties().stream()
                            .map(p -> new CreateAutoIndexStatement.IndexedProperty(p.getName(), p.getBy()))
                            .collect(Collectors.toList());
        } else {
            indexedProperties = Collections.emptyList();
        }

        CreateAutoIndexStatement statement = new CreateAutoIndexStatement()
                .setName(getName())
                .setClassName(className)
                .setType(getType())
                .setIndexedProperties(indexedProperties);

        if (ignoreNullValues != null) {
            statement.setIgnoreNullValues(ignoreNullValues);
        }

        return statement;
    }


    /**
     * Validation group for creation of an automatic index.
     */
    public interface AutoIndex extends Default { }


    /**
     * Validation group for creation of a manual index.
     */
    public interface ManualIndex extends Default { }
}
