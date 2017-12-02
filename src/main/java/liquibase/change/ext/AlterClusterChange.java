package liquibase.change.ext;

import liquibase.database.Database;
import liquibase.statement.SqlStatement;
import org.unbrokendome.liquibase.serialization.Serializable;
import org.unbrokendome.liquibase.serialization.SerializedField;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.stream.Stream;


@Serializable(name = "alterCluster", description = "Modifies attributes of an existing cluster",
		namespace = OrientSerializationConstants.ORIENT_CHANGELOG_NAMESPACE)
public class AlterClusterChange extends AbstractMultiStatementOrientChange {

	private String name;
	private ClusterConfig clusterConfig = new ClusterConfig();


	@SerializedField(description = "The name or ID of the cluster")
	@NotNull
	@Size(min = 1)
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	@SerializedField(embedded = true)
	public ClusterConfig getClusterConfig() {
		return clusterConfig;
	}


	public void setClusterConfig(ClusterConfig clusterConfig) {
		this.clusterConfig = clusterConfig;
	}


	@Override
	public String getConfirmationMessage() {
		return "Cluster " + name + " modified";
	}


	@Override
	protected Stream<SqlStatement> doGenerateStatements(Database database) {
		return clusterConfig.generateStatements(this.name);
	}
}
