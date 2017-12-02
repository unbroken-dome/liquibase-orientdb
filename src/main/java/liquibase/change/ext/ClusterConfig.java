package liquibase.change.ext;

import liquibase.statement.SqlStatement;
import org.unbrokendome.liquibase.orientdb.common.ClusterStatus;
import org.unbrokendome.liquibase.orientdb.common.ConflictStrategy;
import org.unbrokendome.liquibase.orientdb.common.OrientClusterAttribute;
import org.unbrokendome.liquibase.orientdb.statement.SetClusterAttributeStatement;
import org.unbrokendome.liquibase.serialization.AnnotatedSerializable;
import org.unbrokendome.liquibase.serialization.Serializable;
import org.unbrokendome.liquibase.serialization.SerializedField;
import org.unbrokendome.liquibase.validation.OneOfEnum;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;


@Serializable(name = "cluster", namespace = OrientSerializationConstants.ORIENT_CHANGELOG_NAMESPACE)
public class ClusterConfig implements AnnotatedSerializable {

    private String status;
    private String compression;
    private Boolean useWal;
    private Integer recordGrowFactor;
    private Integer recordOverflowGrowFactor;
    private String conflictStrategy;


    @SerializedField
    @OneOfEnum(value = ClusterStatus.class, ignoreCase = true)
    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    @SerializedField
    public String getCompression() {
        return compression;
    }


    public void setCompression(String compression) {
        this.compression = compression;
    }


    @SerializedField
    public Boolean getUseWal() {
        return useWal;
    }


    public void setUseWal(Boolean useWal) {
        this.useWal = useWal;
    }


    @SerializedField
    public Integer getRecordGrowFactor() {
        return recordGrowFactor;
    }


    public void setRecordGrowFactor(Integer recordGrowFactor) {
        this.recordGrowFactor = recordGrowFactor;
    }


    @SerializedField
    public Integer getRecordOverflowGrowFactor() {
        return recordOverflowGrowFactor;
    }


    public void setRecordOverflowGrowFactor(Integer recordOverflowGrowFactor) {
        this.recordOverflowGrowFactor = recordOverflowGrowFactor;
    }


    @SerializedField
    @OneOfEnum(value = ConflictStrategy.class, ignoreCase = true)
    public String getConflictStrategy() {
        return conflictStrategy;
    }


    public void setConflictStrategy(String conflictStrategy) {
        this.conflictStrategy = conflictStrategy;
    }


    public Map<OrientClusterAttribute, Object> getAttributes() {
        Map<OrientClusterAttribute, Object> map = new LinkedHashMap<>();

        if (status != null) {
            map.put(OrientClusterAttribute.STATUS, status.toUpperCase());
        }
        if (compression != null) {
            map.put(OrientClusterAttribute.COMPRESSION, compression);
        }
        if (useWal != null) {
            map.put(OrientClusterAttribute.USE_WAL, useWal);
        }
        if (recordGrowFactor != null) {
            map.put(OrientClusterAttribute.RECORD_GROW_FACTOR, recordGrowFactor);
        }
        if (recordOverflowGrowFactor != null) {
            map.put(OrientClusterAttribute.RECORD_OVERFLOW_GROW_FACTOR, recordOverflowGrowFactor);
        }
        if (conflictStrategy != null) {
            map.put(OrientClusterAttribute.CONFLICTSTRATEGY, conflictStrategy.toLowerCase());
        }
        return map;
    }


    public Stream<SqlStatement> generateStatements(String clusterNameOrId) {
        return getAttributes().entrySet().stream()
                .map(entry -> buildSetClusterAttributeStatement(clusterNameOrId, entry.getKey(), entry.getValue()));
    }





    private SqlStatement buildSetClusterAttributeStatement(String clusterNameOrId,
                                                           OrientClusterAttribute key, Object value) {
        return new SetClusterAttributeStatement()
                .setClusterNameOrId(clusterNameOrId)
                .setAttribute(key)
                .setAttributeValue(value);
    }
}
