package liquibase.change.ext;

import org.unbrokendome.liquibase.serialization.Serializable;


@Serializable(name = "createClass", description = "Creates a new class",
        namespace = OrientSerializationConstants.ORIENT_CHANGELOG_NAMESPACE)
public class CreateClassChange extends AbstractCreateClassChange {

    @Override
    public String getConfirmationMessage() {
        return "Class " + getName() + " created";
    }


    @Override
    protected Class<?>[] getValidationGroups() {
        return new Class<?>[] { IndexConfig.AutoIndex.class };
    }
}
