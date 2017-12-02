package liquibase.change.ext;

import liquibase.database.Database;
import liquibase.statement.SqlStatement;
import org.unbrokendome.liquibase.orientdb.statement.DropIndexStatement;
import org.unbrokendome.liquibase.serialization.Serializable;
import org.unbrokendome.liquibase.serialization.SerializedField;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Serializable(name = "dropIndex", description = "Drops an existing index",
        namespace = OrientSerializationConstants.ORIENT_CHANGELOG_NAMESPACE)
public class DropIndexChange extends AbstractSingleStatementOrientChange {

    private String name;


    @SerializedField(description = "The name of the index")
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
        return "Index " + name + " dropped";
    }


    @Override
    protected SqlStatement doGenerateStatement(Database database) {
        return new DropIndexStatement()
                .setIndexName(name);
    }
}
