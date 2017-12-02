package org.unbrokendome.liquibase.orientdb.structure;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;



public class OrientClass extends AbstractOrientDatabaseObject<OrientClass> {

	private OrientClass superClass;
	private List<OrientCluster> clusters;
	private boolean isAbstract = false;
	
	
	public OrientClass(String name) {
		super(name);
	}

	
	public OrientClass getSuperClass() {
		return superClass;
	}
	
	
	public OrientClass setSuperClass(OrientClass superClass) {
		this.superClass = superClass;
		return this;
	}
	
	
	public OrientClass setSuperClass(String superClassName) {
		return setSuperClass(superClassName != null ? new OrientClass(superClassName) : null);
	}
	
	
	public List<OrientCluster> getClusters() {
		return clusters;
	}
	
	
	public OrientClass setClusters(List<OrientCluster> clusters) {
		setAttribute("clusters", clusters);
		return this;
	}
	
	
	public OrientClass setClustersFromClusterIds(List<String> clusterIds) {
		List<OrientCluster> clusters;
		if (clusterIds != null) {
			clusters = clusterIds.stream()
					.map(OrientCluster::new)
					.collect(Collectors.toList());
		} else {
			clusters = null;
		}
		return setClusters(clusters);
	}
	

	public boolean isAbstract() {
		return isAbstract;
	}
	
	
	public OrientClass setAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
		return this;
	}
}
