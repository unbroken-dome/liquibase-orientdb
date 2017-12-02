package liquibase.sqlgenerator.ext;

import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import org.unbrokendome.liquibase.orientdb.sql.CreateClusterSql;
import org.unbrokendome.liquibase.orientdb.statement.CreateClusterStatement;
import org.unbrokendome.liquibase.orientdb.structure.OrientCluster;


public class CreateClusterSqlGenerator extends AbstractSqlGenerator<CreateClusterStatement> {

    @Override
    public ValidationErrors validate(CreateClusterStatement statement, Database database,
                                     SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("clusterName", statement.getClusterName());
        return validationErrors;
    }


    @Override
    public Sql[] generateSql(CreateClusterStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        OrientCluster orientCluster = new OrientCluster(statement.getClusterName())
                .setClusterId(statement.getClusterId());
        return new Sql[] { new CreateClusterSql(orientCluster) };
    }
}
