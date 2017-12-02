package liquibase.change.ext;

import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.statement.SqlStatement;
import org.unbrokendome.liquibase.serialization.Serializable;
import org.unbrokendome.liquibase.serialization.SerializedField;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.stream.Stream;


@Serializable(name = "createProperty", description = "Creates a new property in an existing class",
        namespace = OrientSerializationConstants.ORIENT_CHANGELOG_NAMESPACE)
public class CreatePropertyChange extends AbstractMultiStatementOrientChange {

    private String className;
    private PropertyConfig propertyConfig = new PropertyConfig();


    @Override
    public ValidationErrors validate(Database database) {
        return super.validate(database);
    }


    @SerializedField(description = "The name of the class that should contain the property")
    @NotNull
    @Size(min = 1)
    public String getClassName() {
        return className;
    }


    public void setClassName(String className) {
        this.className = className;
    }


    @SerializedField(embedded = true)
    @Valid
    public PropertyConfig getPropertyConfig() {
        return propertyConfig;
    }


    public void setPropertyConfig(PropertyConfig propertyConfig) {
        this.propertyConfig = propertyConfig;
    }


    @Override
    public String getConfirmationMessage() {
        return "Property " + className + "." + propertyConfig.getName() + " created";
    }


    @Override
    protected Stream<SqlStatement> doGenerateStatements(Database database) {
        return propertyConfig.generateStatements(className);
    }
}
