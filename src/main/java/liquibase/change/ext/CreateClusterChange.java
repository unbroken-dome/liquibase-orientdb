package liquibase.change.ext;

import liquibase.database.Database;
import liquibase.statement.SqlStatement;
import org.unbrokendome.liquibase.orientdb.statement.CreateClusterStatement;
import org.unbrokendome.liquibase.serialization.Serializable;
import org.unbrokendome.liquibase.serialization.SerializedField;
import org.unbrokendome.liquibase.util.StreamUtils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.stream.Stream;


@Serializable(name = "createCluster", description = "Creates a new cluster",
		namespace = OrientSerializationConstants.ORIENT_CHANGELOG_NAMESPACE)
public class CreateClusterChange extends AbstractMultiStatementOrientChange {

	private String name;
	private Integer id;
	private ClusterConfig clusterConfig = new ClusterConfig();


	@SerializedField(description = "The name of the cluster")
	@NotNull
	@Size(min = 1)
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	@SerializedField(description = "The numeric ID of the cluster")
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
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
		return "Cluster " + name + " created";
	}


	@Override
	protected Stream<SqlStatement> doGenerateStatements(Database database) {
		return Stream.concat(
				StreamUtils.of(this::buildCreateClusterStatement),
				clusterConfig.generateStatements(name));
	}


    private SqlStatement buildCreateClusterStatement() {
		return new CreateClusterStatement()
				.setClusterName(name)
				.setClusterId(id);
    }
}
