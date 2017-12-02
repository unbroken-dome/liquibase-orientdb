package liquibase.change.ext;

import liquibase.database.Database;
import liquibase.statement.SqlStatement;
import org.unbrokendome.liquibase.orientdb.statement.DropClusterStatement;
import org.unbrokendome.liquibase.serialization.Serializable;
import org.unbrokendome.liquibase.serialization.SerializedField;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Serializable(name = "dropCluster", description = "Drops an existing cluster",
		namespace = OrientSerializationConstants.ORIENT_CHANGELOG_NAMESPACE)
public class DropClusterChange extends AbstractSingleStatementOrientChange {

	private String clusterName;


	@SerializedField(description = "Name or ID of the cluster")
	@NotNull
	@Size(min = 1)
	public String getClusterName() {
		return clusterName;
	}


	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}


	@Override
	public String getConfirmationMessage() {
		return "Cluster " + clusterName + " dropped";
	}


	@Override
	protected SqlStatement doGenerateStatement(Database database) {
		return new DropClusterStatement()
				.setClusterNameOrId(clusterName);
	}
}
