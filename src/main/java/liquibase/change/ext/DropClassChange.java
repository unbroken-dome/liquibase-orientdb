package liquibase.change.ext;

import liquibase.database.Database;
import liquibase.statement.SqlStatement;
import org.unbrokendome.liquibase.orientdb.statement.DropClassStatement;
import org.unbrokendome.liquibase.serialization.Serializable;
import org.unbrokendome.liquibase.serialization.SerializedField;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Serializable(name = "dropClass", description = "Drops an existing class",
		namespace = OrientSerializationConstants.ORIENT_CHANGELOG_NAMESPACE)
public class DropClassChange extends AbstractSingleStatementOrientChange {

	private String name;


	@SerializedField(description = "The name of the class")
	@NotNull
	@Size(min = 1)
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	@Override
	public String getConfirmationMessage() {
		return "Class " + name + " dropped";
	}


    @Override
    protected SqlStatement doGenerateStatement(Database database) {
        return new DropClassStatement()
                .setClassName(name);
    }
}
