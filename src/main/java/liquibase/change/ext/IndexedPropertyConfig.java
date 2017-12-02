package liquibase.change.ext;

import org.unbrokendome.liquibase.serialization.AnnotatedSerializable;
import org.unbrokendome.liquibase.serialization.Serializable;
import org.unbrokendome.liquibase.serialization.SerializedField;


@Serializable(name = "indexedProperty", namespace = OrientSerializationConstants.ORIENT_CHANGELOG_NAMESPACE)
public class IndexedPropertyConfig implements AnnotatedSerializable {

    private String name;
    private String by;


    public IndexedPropertyConfig() {
    }


    public IndexedPropertyConfig(String name) {
        this.name = name;
    }


    public IndexedPropertyConfig(String name, String by) {
        this(name);
        this.by = by;
    }


    @SerializedField
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    @SerializedField
    public String getBy() {
        return by;
    }


    public void setBy(String by) {
        this.by = by;
    }
}
