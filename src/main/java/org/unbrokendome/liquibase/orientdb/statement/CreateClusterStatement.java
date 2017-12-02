package org.unbrokendome.liquibase.orientdb.statement;

public class CreateClusterStatement extends AbstractClusterStatement<CreateClusterStatement> {
	
	private String clusterName;
	private Integer clusterId;

	
	public String getClusterName() {
		return clusterName;
	}
	
	
	public CreateClusterStatement setClusterName(String clusterName) {
		this.clusterName = clusterName;
		return this;
	}


	public Integer getClusterId() {
		return clusterId;
	}


	public CreateClusterStatement setClusterId(Integer clusterId) {
		this.clusterId = clusterId;
		return this;
	}
}
