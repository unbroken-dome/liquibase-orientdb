package liquibase.change.ext;

import liquibase.database.Database;
import liquibase.statement.SqlStatement;
import org.unbrokendome.liquibase.orientdb.statement.RenameClassStatement;
import org.unbrokendome.liquibase.serialization.Serializable;
import org.unbrokendome.liquibase.serialization.SerializedField;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Serializable(name = "renameClass", description = "Renames an existing class",
        namespace = OrientSerializationConstants.ORIENT_CHANGELOG_NAMESPACE)
public class RenameClassChange extends AbstractSingleStatementOrientChange {

    private String from;
    private String to;


    @SerializedField(description = "The name of the existing class")
    @NotNull
    @Size(min = 1)
    public String getFrom() {
        return from;
    }


    public void setFrom(String from) {
        this.from = from;
    }


    @SerializedField(description = "The new name for the class")
    @NotNull
    @Size(min = 1)
    public String getTo() {
        return to;
    }


    public void setTo(String to) {
        this.to = to;
    }


    @Override
    public String getConfirmationMessage() {
        return "Class " + from + " renamed to " + to;
    }


    @Override
    protected SqlStatement doGenerateStatement(Database database) {
        return new RenameClassStatement()
                .setClassName(from)
                .setNewName(to);
    }
}
