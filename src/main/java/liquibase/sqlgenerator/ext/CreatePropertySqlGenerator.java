package liquibase.sqlgenerator.ext;

import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import org.apache.commons.lang3.StringUtils;
import org.unbrokendome.liquibase.orientdb.common.OrientPropertyType;
import org.unbrokendome.liquibase.orientdb.sql.CreatePropertySql;
import org.unbrokendome.liquibase.orientdb.statement.CreatePropertyStatement;
import org.unbrokendome.liquibase.orientdb.structure.OrientClass;
import org.unbrokendome.liquibase.orientdb.structure.OrientProperty;


public class CreatePropertySqlGenerator extends AbstractSqlGenerator<CreatePropertyStatement> {

    @Override
    public ValidationErrors validate(CreatePropertyStatement statement, Database database,
                                     SqlGeneratorChain sqlGeneratorChain) {

        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("className", statement.getClassName());
        validationErrors.checkRequiredField("propertyName", statement.getPropertyName());
        validationErrors.checkRequiredField("type", statement.getType());


        if (!isValidDataType(statement.getType())) {
            validationErrors.addError("Property type \"" + statement.getType() + "\" is not a valid type");
            return validationErrors;
        }

        OrientPropertyType type;
        try {
            type = OrientPropertyType.valueOf(statement.getType().toUpperCase());
        } catch (IllegalArgumentException e) {
            validationErrors.addError("Property type \"" + statement.getType() + "\" is not a valid type");
            return validationErrors;
        }

        boolean hasLinkedType = StringUtils.isNotEmpty(statement.getLinkedType());

        if ((type.isLink() || type.isEmbedded()) && !hasLinkedType) {
            validationErrors.addError("Properties of type " + type + " must have a linked type");
        }

        return validationErrors;
    }


    private boolean isValidDataType(String dataTypeName) {
        if (dataTypeName == null) {
            return true;
        }
        try {
            OrientPropertyType.valueOf(dataTypeName.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }


    @Override
    public Sql[] generateSql(CreatePropertyStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {

        OrientClass orientClass = new OrientClass(statement.getClassName());
        OrientProperty property = new OrientProperty(orientClass, statement.getPropertyName())
                .setType(OrientPropertyType.parse(statement.getType()))
                .setUnsafe(statement.isUnsafe());

        String linkedTypeString = statement.getLinkedType();
        if (StringUtils.isNotEmpty(linkedTypeString)) {
            OrientPropertyType linkedType = OrientPropertyType.tryParse(linkedTypeString);
            if (linkedType != null) {
                property.setLinkedType(linkedType);
            } else {
                OrientClass linkedClass = new OrientClass(linkedTypeString);
                property.setLinkedClass(linkedClass);
            }
        }

        return new Sql[] { new CreatePropertySql(property) };
    }
}
