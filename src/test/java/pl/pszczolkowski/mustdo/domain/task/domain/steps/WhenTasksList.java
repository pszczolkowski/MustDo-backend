package pl.pszczolkowski.mustdo.domain.task.domain.steps;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import pl.pszczolkowski.mustdo.domain.task.dto.TasksListSnapshot;
import pl.pszczolkowski.mustdo.domain.task.entity.TasksList;
import pl.pszczolkowski.mustdo.sharedkernel.exception.EntityInStateNewException;

public class WhenTasksList extends Stage<WhenTasksList>{
	
	@ExpectedScenarioState
	private TasksList tasksList;
	
	@ProvidedScenarioState
	private boolean entityInStateNewWasThrown;
	@ProvidedScenarioState
	private TasksListSnapshot tasksListSnapshot;
	@ProvidedScenarioState
	private String newName = WhenTasksList.class.getSimpleName();
	
	public void to_snapshot_is_invoked() {
		try{
			tasksListSnapshot = tasksList.toSnapshot();
		}catch(EntityInStateNewException e){
			entityInStateNewWasThrown = true;
		}
	}

	public void rename_is_invoked() {
		tasksList.rename(newName);
		tasksListSnapshot = tasksList.toSnapshot();
	}

}
