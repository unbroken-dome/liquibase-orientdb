package liquibase.changelog.ext;

import java.util.List;

import com.google.common.collect.Iterables;

import liquibase.changelog.StandardChangeLogHistoryService;
import liquibase.database.Database;
import liquibase.database.ext.OrientDatabase;
import liquibase.exception.LiquibaseException;
import liquibase.executor.Executor;
import liquibase.executor.ExecutorService;
import liquibase.statement.core.GetNextChangeSetSequenceValueStatement;


public class OrientChangeLogHistoryService extends StandardChangeLogHistoryService {

	private Integer lastChangeSetSequenceValue;
	
	
	@Override
	public boolean supports(Database database) {
		return database instanceof OrientDatabase;
	}
	
	
	@Override
	public int getPriority() {
		return PRIORITY_DATABASE;
	}
	
	
    @SuppressWarnings({"unchecked", "ConstantConditions"})
    @Override
    public int getNextSequenceValue() throws LiquibaseException {
        if (lastChangeSetSequenceValue == null) {
            if (getDatabase().getConnection() == null) {
                lastChangeSetSequenceValue = 0;
            } else {
                Executor executor = ExecutorService.getInstance().getExecutor(getDatabase());
                
                List<Integer> list = executor.queryForList(new GetNextChangeSetSequenceValueStatement(),
                		Integer.class);

                lastChangeSetSequenceValue = Iterables.getFirst(list, 0);
            }
        }

        return ++lastChangeSetSequenceValue;
    }
	
}
