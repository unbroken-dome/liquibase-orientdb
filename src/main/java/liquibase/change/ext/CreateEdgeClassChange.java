package liquibase.change.ext;

import liquibase.change.DatabaseChangeProperty;
import org.unbrokendome.liquibase.orientdb.statement.CreateClassStatement;
import org.unbrokendome.liquibase.orientdb.statement.CreatePropertyStatement;
import org.unbrokendome.liquibase.serialization.Serializable;
import org.unbrokendome.liquibase.serialization.SerializedField;
import org.unbrokendome.liquibase.util.StreamUtils;
import com.orientechnologies.orient.core.metadata.schema.OType;
import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.database.Database;
import liquibase.statement.SqlStatement;

import java.util.Objects;
import java.util.stream.Stream;


@Serializable(name = "createEdgeClass", description = "Creates a new edge class",
        namespace = OrientSerializationConstants.ORIENT_CHANGELOG_NAMESPACE)
public class CreateEdgeClassChange extends AbstractCreateClassChange {

    private String from;
    private String to;
    private Boolean createLinkProperties;
    private Boolean createVertexLinkProperties;


    @Override
    public String getConfirmationMessage() {
        return "Edge class " + getName() + " created";
    }


    @SerializedField(description = "The vertex class that this edge should originate from")
    public String getFrom() {
        return from;
    }


    public void setFrom(String from) {
        this.from = from;
    }


    @SerializedField(description = "The vertex class that this edge should lead to")
    public String getTo() {
        return to;
    }


    public void setTo(String to) {
        this.to = to;
    }


    @SerializedField(
            description = "Defines whether properties that link to the vertices should be created on the edge class")
    public Boolean getCreateLinkProperties() {
        return createLinkProperties;
    }


    public void setCreateLinkProperties(Boolean createLinkProperties) {
        this.createLinkProperties = createLinkProperties;
    }


    @SerializedField(
            description = "Defines whether properties that link to the edge should be created on the vertex classes")
    public Boolean getCreateVertexLinkProperties() {
        return createVertexLinkProperties;
    }


    public void setCreateVertexLinkProperties(Boolean createVertexLinkProperties) {
        this.createVertexLinkProperties = createVertexLinkProperties;
    }


    @Override
    protected void adjustCreateClassStatement(CreateClassStatement statement) {
        // Superclass defaults to "E" if no explicit superclass is given
        if (getSuperClassName() == null) {
            statement.setSuperClassName("E");
        }
    }


    @Override
    protected Stream<SqlStatement> generateAdditionalPropertyStatements(Database database) {
        return generateEdgeLinkPropertiesStatements();
    }


    @Override
    protected Stream<SqlStatement> generateAdditionalStatements(Database database) {
        return generateVertexLinkPropertiesStatements();
    }


    private Stream<SqlStatement> generateEdgeLinkPropertiesStatements() {
        if (Boolean.TRUE.equals(getCreateLinkProperties())) {
            return StreamUtils.of(
                    this::createEdgeOutPropertyStatement,
                    this::createEdgeInPropertyStatement)
                    .filter(Objects::nonNull);

        } else {
            return Stream.empty();
        }
    }


    private SqlStatement createEdgeOutPropertyStatement() {
        if (this.getFrom() != null) {
            return new CreatePropertyStatement()
                    .setClassName(this.getName())
                    .setPropertyName("out")
                    .setType(OType.LINK.toString())
                    .setLinkedType(this.getFrom());
        } else {
            return null;
        }
    }


    private SqlStatement createEdgeInPropertyStatement() {
        if (this.getTo() != null) {
            return new CreatePropertyStatement()
                    .setClassName(this.getName())
                    .setPropertyName("in")
                    .setType(OType.LINK.toString())
                    .setLinkedType(this.getTo());
        } else {
            return null;
        }
    }


    private Stream<SqlStatement> generateVertexLinkPropertiesStatements() {
        if (Boolean.TRUE.equals(getCreateVertexLinkProperties())) {
            return StreamUtils.of(
                    this::createVertexOutPropertyStatement,
                    this::createVertexInPropertyStatement)
                    .filter(Objects::nonNull);

        } else {
            return Stream.empty();
        }
    }


    private SqlStatement createVertexOutPropertyStatement() {
        if (this.getFrom() != null) {
            return new CreatePropertyStatement()
                    .setClassName(this.getFrom())
                    .setPropertyName("out_" + this.getName())
                    .setType(OType.LINKLIST.toString())
                    .setLinkedType(this.getName());
        } else {
            return null;
        }
    }


    private SqlStatement createVertexInPropertyStatement() {
        if (this.getTo() != null) {
            return new CreatePropertyStatement()
                    .setClassName(this.getTo())
                    .setPropertyName("in_" + this.getName())
                    .setType(OType.LINKLIST.toString())
                    .setLinkedType(this.getName());
        } else {
            return null;
        }
    }
}
