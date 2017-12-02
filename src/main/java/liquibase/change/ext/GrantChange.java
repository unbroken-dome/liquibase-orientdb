package liquibase.change.ext;

import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.database.Database;
import liquibase.statement.SqlStatement;
import org.unbrokendome.liquibase.orientdb.common.OrientPermission;
import org.unbrokendome.liquibase.orientdb.statement.GrantStatement;
import org.unbrokendome.liquibase.serialization.Serializable;
import org.unbrokendome.liquibase.serialization.SerializedField;
import org.unbrokendome.liquibase.validation.OneOfEnum;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Serializable(name = "grant", description = "Grants a permission to a role",
        namespace = OrientSerializationConstants.ORIENT_CHANGELOG_NAMESPACE)
public class GrantChange extends AbstractSingleStatementOrientChange {

    private String permission;
    private String on;
    private String to;


    @SerializedField(description = "The permission to grant")
    @NotNull
    @OneOfEnum(value = OrientPermission.class, ignoreCase = true)
    public String getPermission() {
        return permission;
    }


    public void setPermission(String permission) {
        this.permission = permission;
    }


    @SerializedField(description = "The resource on which to grant permissions")
    @NotNull
    @Size(min = 1)
    public String getOn() {
        return on;
    }


    public void setOn(String on) {
        this.on = on;
    }


    @SerializedField(description = "The role to which to grant permissions")
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
        return "Permission " + permission.toUpperCase()
                + " on " + on.toUpperCase()
                + " granted to " + to;
    }


    @Override
    protected SqlStatement doGenerateStatement(Database database) {
        return new GrantStatement()
                .setPermission(permission)
                .setResource(on)
                .setRole(to);
    }
}
