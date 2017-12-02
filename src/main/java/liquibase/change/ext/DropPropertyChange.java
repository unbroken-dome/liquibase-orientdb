package liquibase.change.ext;

import liquibase.database.Database;
import liquibase.statement.SqlStatement;
import org.unbrokendome.liquibase.orientdb.statement.DropPropertyStatement;
import org.unbrokendome.liquibase.serialization.Serializable;
import org.unbrokendome.liquibase.serialization.SerializedField;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Serializable(name = "dropProperty", description = "Drops an existing property",
        namespace = OrientSerializationConstants.ORIENT_CHANGELOG_NAMESPACE)
public class DropPropertyChange extends AbstractSingleStatementOrientChange {

	private String className;
	private String propertyName;


    @SerializedField(description = "The name of the class that contains the property")
    @NotNull
    @Size(min = 1)
    public String getClassName() {
        return className;
    }


    public void setClassName(String className) {
        this.className = className;
    }


    @SerializedField(description = "The name of the property")
    @NotNull
    @Size(min = 1)
    public String getPropertyName() {
        return propertyName;
    }


    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }


    @Override
	public String getConfirmationMessage() {
		return "Property " + className + "." + propertyName + " dropped";
	}


	@Override
	protected SqlStatement doGenerateStatement(Database database) {
		return new DropPropertyStatement()
				.setClassName(className)
				.setPropertyName(propertyName);
	}
}
