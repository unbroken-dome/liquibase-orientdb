package liquibase.sqlgenerator.ext;


import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import org.unbrokendome.liquibase.orientdb.sql.DropClassSql;
import org.unbrokendome.liquibase.orientdb.sql.DropClusterSql;
import org.unbrokendome.liquibase.orientdb.statement.DropClassStatement;
import org.unbrokendome.liquibase.orientdb.statement.DropClusterStatement;
import org.unbrokendome.liquibase.orientdb.structure.OrientClass;
import org.unbrokendome.liquibase.orientdb.structure.OrientCluster;


public class DropClassSqlGenerator extends AbstractSqlGenerator<DropClassStatement> {

    @Override
    public ValidationErrors validate(DropClassStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("className", statement.getClassName());
        return validationErrors;
    }


    @Override
    public Sql[] generateSql(DropClassStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {

        OrientClass orientClass = new OrientClass(statement.getClassName());

        return new Sql[] { new DropClassSql(orientClass, statement.isUnsafe()) };
    }
}
