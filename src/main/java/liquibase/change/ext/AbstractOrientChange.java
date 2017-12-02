package liquibase.change.ext;

import liquibase.change.*;
import liquibase.changelog.ChangeSet;
import liquibase.database.Database;
import liquibase.database.ext.OrientDatabase;
import liquibase.database.ext.OrientDatabaseConstants;
import liquibase.exception.*;
import liquibase.resource.ResourceAccessor;
import liquibase.serializer.core.string.StringChangeLogSerializer;
import liquibase.sqlgenerator.SqlGeneratorFactory;
import liquibase.statement.SqlStatement;
import liquibase.structure.DatabaseObject;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.unbrokendome.liquibase.serialization.AnnotatedSerializable;
import org.unbrokendome.liquibase.serialization.AnnotationReflectionSerializer;
import org.unbrokendome.liquibase.serialization.SerializedClassInfo;

import javax.validation.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;


public abstract class AbstractOrientChange implements AnnotatedSerializable, Change {

    private static final ValidatorFactory VALIDATOR_FACTORY;

    static {
        Configuration<?> validatorConfiguration = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator());
        VALIDATOR_FACTORY = validatorConfiguration.buildValidatorFactory();
    }


    private ChangeSet changeSet;
    private ResourceAccessor resourceAccessor;


	@Override
	public final boolean supports(Database database) {
		return database instanceof OrientDatabase;
	}


    @Override
    public void finishInitialization() throws SetupException {
    }


    @Override
    public ChangeMetaData createChangeMetaData() {
        try {
            SerializedClassInfo serializedClassInfo = AnnotationReflectionSerializer.getInstance().getSerializedClassInfo(this);
            Set<ChangeParameterMetaData> params = serializedClassInfo.getFields().stream()
                    .map(fieldInfo -> new ChangeParameterMetaData(
                            this, fieldInfo.getName(), fieldInfo.getName(), fieldInfo.getDescription(),
                            Collections.singletonMap("all", ""),
                            null,
                            fieldInfo.getType(),
                            new String[] { "none" },
                            new String[] { OrientDatabaseConstants.SHORT_NAME },
                            null,
                            fieldInfo.getSerializationType()))
                    .collect(Collectors.toSet());

            return new ChangeMetaData(
                    serializedClassInfo.getName(),
                    serializedClassInfo.getDescription(),
                    ChangeMetaData.PRIORITY_DATABASE,
                    new String[] { OrientDatabaseConstants.SHORT_NAME },
                    Collections.emptyMap(),
                    params);

        } catch (Exception e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }


    @Override
    public final ChangeSet getChangeSet() {
        return changeSet;
    }


    @Override
    public final void setChangeSet(ChangeSet changeSet) {
        this.changeSet = changeSet;
    }


    protected final ResourceAccessor getResourceAccessor() {
        return resourceAccessor;
    }


    @Override
    public final void setResourceAccessor(ResourceAccessor resourceAccessor) {
        this.resourceAccessor = resourceAccessor;
    }


    @Override
    public Warnings warn(Database database) {
        return null;
    }


    @Override
    public ValidationErrors validate(Database database) {
        Validator validator = VALIDATOR_FACTORY.getValidator();

        AnnotationReflectionSerializer reflectionSerializer = AnnotationReflectionSerializer.getInstance();

        ValidationErrors validationErrors = new ValidationErrors();
        for (ConstraintViolation<?> violation : validator.validate(this, getValidationGroups())) {

            String serializedPath = reflectionSerializer.translatePropertyPathToSerializedPath(
                    this, violation.getPropertyPath());
            validationErrors.addError(serializedPath + ": " + violation.getMessage());
        }

        return validationErrors;
    }


    protected Class<?>[] getValidationGroups() {
        return new Class<?>[0];
    }


    @Override
    public Set<DatabaseObject> getAffectedDatabaseObjects(Database database) {
        if (this.generateStatementsVolatile(database)) {
            return Collections.emptySet();
        }

        SqlStatement[] statements = generateStatements(database);
        if (statements != null) {
            SqlGeneratorFactory sqlGeneratorFactory = SqlGeneratorFactory.getInstance();
            return Arrays.stream(statements)
                    .flatMap(stmt -> sqlGeneratorFactory.getAffectedDatabaseObjects(stmt, database).stream())
                    .collect(Collectors.toSet());
        }

        return Collections.emptySet();
    }


    @Override
    public CheckSum generateCheckSum() {
        return CheckSum.compute(new StringChangeLogSerializer().serialize(this, false));
    }


    @Override
    public boolean generateStatementsVolatile(Database database) {
        SqlStatement[] statements = generateStatements(database);
        if (statements == null) {
            return false;
        }

        SqlGeneratorFactory sqlGeneratorFactory = SqlGeneratorFactory.getInstance();
        return Arrays.stream(statements)
                .anyMatch(stmt -> sqlGeneratorFactory.generateStatementsVolatile(stmt, database));
    }


    @Override
    public boolean supportsRollback(Database database) {
        return false;
    }


    @Override
    public SqlStatement[] generateRollbackStatements(Database database) throws RollbackImpossibleException {
        throw new RollbackImpossibleException();
    }


    @Override
    public boolean generateRollbackStatementsVolatile(Database database) {
        if (generateStatementsVolatile(database)) {
            return true;
        }
        SqlStatement[] statements = generateStatements(database);
        if (statements == null) {
            return false;
        }
        SqlGeneratorFactory sqlGeneratorFactory = SqlGeneratorFactory.getInstance();
        return Arrays.stream(statements)
                .anyMatch(stmt -> sqlGeneratorFactory.generateRollbackStatementsVolatile(stmt, database));
    }


    @Override
    public ChangeStatus checkStatus(Database database) {
        return new ChangeStatus().unknown("Not implemented");
    }


    @Override
    public String getDescription() {
        return null;
    }
}
