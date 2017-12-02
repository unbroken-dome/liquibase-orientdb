package liquibase.change.ext;


import liquibase.database.Database;
import liquibase.statement.SqlStatement;
import org.unbrokendome.liquibase.serialization.Serializable;
import org.unbrokendome.liquibase.serialization.SerializedField;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Serializable(name = "createIndex", description = "Creates a new index",
        namespace = OrientSerializationConstants.ORIENT_CHANGELOG_NAMESPACE)
public class CreateIndexChange extends AbstractSingleStatementOrientChange {

    private String className;
    private IndexConfig indexConfig = new IndexConfig();
    private List<String> keyTypes = new ArrayList<>();


    @SerializedField(name="on", description = "The name of the class to create an automatic index for")
    public String getClassName() {
        return className;
    }


    public void setClassName(String className) {
        this.className = className;
    }


    @SerializedField(embedded = true)
    @Valid
    public IndexConfig getIndexConfig() {
        return indexConfig;
    }


    public void setIndexConfig(IndexConfig indexConfig) {
        this.indexConfig = indexConfig;
    }


    @SerializedField(name = "keyType")
    public String getKeyType() {
        if (keyTypes != null && !keyTypes.isEmpty()) {
            return String.join(" ", keyTypes);
        } else {
            return null;
        }
    }


    public void setKeyType(String keyType) {
        if (keyType != null) {
            this.keyTypes = Arrays.asList(keyType.split("\\s+"));
        } else {
            this.keyTypes = Collections.emptyList();
        }
    }


    public List<String> getKeyTypes() {
        return keyTypes;
    }


    public void setKeyTypes(List<String> keyTypes) {
        this.keyTypes = keyTypes;
    }


    @Override
    public String getConfirmationMessage() {
        return "Index " + indexConfig.getName() + " created";
    }


    @Override
    protected SqlStatement doGenerateStatement(Database database) {

        if (className != null) {
            return indexConfig.generateStatementForAutoIndex(className);

        } else {
            return indexConfig.generateStatementForManualIndex(keyTypes);
        }
    }


    @Override
    protected Class<?>[] getValidationGroups() {
        Class<?> group = (this.className != null) ? IndexConfig.AutoIndex.class : IndexConfig.ManualIndex.class;
        return new Class<?>[] { group };
    }
}
