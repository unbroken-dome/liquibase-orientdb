package org.unbrokendome.liquibase.orientdb.structure;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import org.unbrokendome.liquibase.orientdb.common.OrientPropertyType;
import com.google.common.collect.ImmutableList;

public class OrientIndex extends AbstractOrientDatabaseObject<OrientIndex> {

	private OrientClass orientClass;
	private List<OrientIndexedProperty> indexedProperties;
	private OrientIndexType type;
	private String engine;
	private List<OrientPropertyType> keyTypes;
	private boolean ignoreNullValues = true;
    private boolean hasCustomName = false;


    @Override
    public OrientIndex setName(String name) {
        hasCustomName = (name != null);
        return super.setName(name);
    }


    public OrientClass getOrientClass() {
		return orientClass;
	}


	public OrientIndex setOrientClass(OrientClass orientClass) {
		this.orientClass = orientClass;
        autoGenerateNameIfNeeded();
		return this;
	}


    public List<OrientIndexedProperty> getIndexedProperties() {
        return indexedProperties;
    }


    public OrientIndex setIndexedProperties(List<OrientIndexedProperty> indexedProperties) {
        this.indexedProperties = indexedProperties;
        autoGenerateNameIfNeeded();
        return this;
    }


    public List<String> getIndexedPropertyNames() {
        if (indexedProperties == null) {
            return Collections.emptyList();
        }
        return indexedProperties.stream()
                .map(p -> p.getProperty().getName())
                .collect(Collectors.toList());
    }


    public OrientIndexType getType() {
		return type;
	}


	public OrientIndex setType(OrientIndexType type) {
		this.type = type;
		return this;
	}


	public String getEngine() {
		return engine;
	}


	public OrientIndex setEngine(String engine) {
		this.engine = engine;
		return this;
	}


	public List<OrientPropertyType> getKeyTypes() {
		return keyTypes;
	}
	
	
	public OrientIndex setKeyTypes(List<OrientPropertyType> keyTypes) {
		this.keyTypes = ImmutableList.copyOf(keyTypes);
		return this;
	}
	
	
	public OrientIndex setKeyType(OrientPropertyType keyType) {
		return setKeyTypes(ImmutableList.of(keyType));
	}


	public boolean isIgnoreNullValues() {
		return ignoreNullValues;
	}


	public OrientIndex setIgnoreNullValues(boolean ignoreNullValues) {
		this.ignoreNullValues = ignoreNullValues;
		return this;
	}
	
	
	public boolean isAutoIndex() {
		return this.orientClass != null;
	}


	private void autoGenerateNameIfNeeded() {
		if (!hasCustomName && orientClass != null && indexedProperties != null && indexedProperties.size() == 1) {
			setName(orientClass.getName() + "." + indexedProperties.get(0).getProperty().getName());
		}
	}


	public boolean hasCustomName() {
	    return hasCustomName;
    }
}
