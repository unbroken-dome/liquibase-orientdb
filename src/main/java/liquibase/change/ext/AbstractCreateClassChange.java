package liquibase.change.ext;

import org.unbrokendome.liquibase.orientdb.statement.CreateClassStatement;
import org.unbrokendome.liquibase.serialization.SerializedField;
import org.unbrokendome.liquibase.util.StreamUtils;
import liquibase.database.Database;
import liquibase.statement.SqlStatement;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


public abstract class AbstractCreateClassChange extends AbstractMultiStatementOrientChange {

    private String name;
    private String superClassName;
    private List<String> clusterIds;
    private Boolean isAbstract;
    private List<PropertyConfig> properties = new ArrayList<>();
    private List<IndexConfig> indexes = new ArrayList<>();


    @SerializedField(description = "The name of the class")
    @NotNull
    @Size(min = 1)
    public String getName() {
        return name;
    }


    public void setName(String className) {
        this.name = className;
    }


    @SerializedField(name = "extends", description = "The superclass to extend")
    public String getSuperClassName() {
        return superClassName;
    }


    public void setSuperClassName(String superClassName) {
        this.superClassName = superClassName;
    }


    @SerializedField(description = "The IDs of the clusters that the class should use")
    public List<String> getClusterIds() {
        return clusterIds;
    }


    public void setClusterIds(List<String> clusterIds) {
        this.clusterIds = clusterIds;
    }


    @SerializedField(name = "abstract", description = "Defines whether the class is abstract")
    public Boolean getAbstract() {
        return isAbstract;
    }


    public void setAbstract(Boolean isAbstract) {
        this.isAbstract = isAbstract;
    }


    @SerializedField(name = "property", description = "Defines the properties of the class",
            type = SerializationType.NESTED_OBJECT)
    public List<PropertyConfig> getProperties() {
        return properties;
    }


    public void setProperties(List<PropertyConfig> properties) {
        this.properties = properties;
    }


    @SerializedField(name = "index", description = "Defines the indexes of the class",
            type = SerializationType.NESTED_OBJECT)
    @Valid
    public List<IndexConfig> getIndexes() {
        return indexes;
    }


    public void setIndexes(List<IndexConfig> indexes) {
        this.indexes = indexes;
    }


    @Override
    protected final Stream<SqlStatement> doGenerateStatements(Database database) {
        return StreamUtils.concat(
                StreamUtils.of(this::buildCreateClassStatement),
                properties.stream()
                        .flatMap(property -> property.generateStatements(name)),
                generateAdditionalPropertyStatements(database),
                indexes.stream()
                        .flatMap(index -> Stream.of(index.generateStatementForAutoIndex(name))),
                generateAdditionalStatements(database));
    }


    protected Stream<SqlStatement> generateAdditionalPropertyStatements(Database database) {
        return Stream.empty();
    }


    protected Stream<SqlStatement> generateAdditionalStatements(Database database) {
        return Stream.empty();
    }


    private CreateClassStatement buildCreateClassStatement() {
        CreateClassStatement classStatement = new CreateClassStatement()
                .setClassName(name)
                .setSuperClassName(superClassName)
                .setClusterIds(clusterIds);
        if (isAbstract != null) {
            classStatement.setAbstract(isAbstract);
        }

        adjustCreateClassStatement(classStatement);

        return classStatement;
    }


    protected void adjustCreateClassStatement(CreateClassStatement statement) {
        // to be overridden by subclass
    }


    @Override
    protected Class<?>[] getValidationGroups() {
        return new Class<?>[] { IndexConfig.AutoIndex.class };
    }
}
