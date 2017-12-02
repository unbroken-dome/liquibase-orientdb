package liquibase.sqlgenerator.ext;


import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import org.unbrokendome.liquibase.orientdb.sql.DropClusterSql;
import org.unbrokendome.liquibase.orientdb.statement.DropClusterStatement;
import org.unbrokendome.liquibase.orientdb.structure.OrientCluster;


public class DropClusterSqlGenerator extends AbstractSqlGenerator<DropClusterStatement> {

    @Override
    public ValidationErrors validate(DropClusterStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("clusterNameOrId", statement.getClusterNameOrId());
        return validationErrors;
    }


    @Override
    public Sql[] generateSql(DropClusterStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {

        OrientCluster cluster = new OrientCluster(statement.getClusterNameOrId());

        return new Sql[] { new DropClusterSql(cluster) };
    }
}
