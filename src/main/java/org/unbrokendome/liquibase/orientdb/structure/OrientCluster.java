package org.unbrokendome.liquibase.orientdb.structure;



public class OrientCluster extends AbstractOrientDatabaseObject<OrientCluster> {

	private Integer clusterId;

	public OrientCluster(String name) {
		super(name);
	}


	public Integer getClusterId() {
		return clusterId;
	}


	public OrientCluster setClusterId(Integer clusterId) {
		this.clusterId = clusterId;
		return this;
	}
}
