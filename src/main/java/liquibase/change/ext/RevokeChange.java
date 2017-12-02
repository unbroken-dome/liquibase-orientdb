package liquibase.change.ext;

import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.database.Database;
import liquibase.statement.SqlStatement;
import org.unbrokendome.liquibase.orientdb.common.OrientPermission;
import org.unbrokendome.liquibase.orientdb.statement.RevokeStatement;
import org.unbrokendome.liquibase.serialization.Serializable;
import org.unbrokendome.liquibase.serialization.SerializedField;
import org.unbrokendome.liquibase.validation.OneOfEnum;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Serializable(name = "property", description = "Revokes a permission from a role",
        namespace = OrientSerializationConstants.ORIENT_CHANGELOG_NAMESPACE)
public class RevokeChange extends AbstractSingleStatementOrientChange {

    private String permission;
    private String on;
    private String from;


    @SerializedField(description = "The permission to revoke")
    @NotNull
    @OneOfEnum(value = OrientPermission.class, ignoreCase = true)
    public String getPermission() {
        return permission;
    }


    public void setPermission(String permission) {
        this.permission = permission;
    }


    @SerializedField(description = "The resource on which to revoke permissions")
    @NotNull
    @Size(min = 1)
    public String getOn() {
        return on;
    }


    public void setOn(String on) {
        this.on = on;
    }


    @SerializedField(description = "The role from which to revoke permissions")
    @NotNull
    @Size(min = 1)
    public String getFrom() {
        return from;
    }


    public void setFrom(String from) {
        this.from = from;
    }


    @Override
    public String getConfirmationMessage() {
        return "Permission " + permission.toUpperCase()
                + " on " + on.toUpperCase()
                + " revoked from " + from;
    }


    @Override
    protected SqlStatement doGenerateStatement(Database database) {
        return new RevokeStatement()
                .setPermission(permission)
                .setResource(on)
                .setRole(from);
    }
}
