package liquibase.change.ext;

import org.unbrokendome.liquibase.serialization.AnnotatedSerializable;
import org.unbrokendome.liquibase.serialization.Serializable;
import org.unbrokendome.liquibase.serialization.SerializedField;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Serializable(name = "field", namespace = OrientSerializationConstants.ORIENT_CHANGELOG_NAMESPACE)
public class FieldConfig implements AnnotatedSerializable {

    private String name;
    private String value;
    private String expression;


    @SerializedField
    @NotNull
    @Size(min = 1)
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    @SerializedField
    @Size(min = 1)
    public String getValue() {
        return value;
    }


    public void setValue(String value) {
        this.value = value;
    }


    @SerializedField
    @Size(min = 1)
    public String getExpression() {
        return expression;
    }


    public void setExpression(String expression) {
        this.expression = expression;
    }
}
