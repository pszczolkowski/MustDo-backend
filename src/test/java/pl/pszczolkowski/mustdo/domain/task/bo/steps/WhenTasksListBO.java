package pl.pszczolkowski.mustdo.domain.task.bo.steps;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import pl.pszczolkowski.mustdo.domain.board.dto.BoardSnapshot;
import pl.pszczolkowski.mustdo.domain.task.bo.TasksListBO;
import pl.pszczolkowski.mustdo.domain.task.dto.TasksListSnapshot;
import pl.pszczolkowski.mustdo.domain.task.exception.TasksListAlreadyExistException;

public class WhenTasksListBO extends Stage<WhenTasksListBO>{
	
	private static final String CLAZZ = WhenTasksListBO.class.getSimpleName();
	@ExpectedScenarioState
	private String name;
	@ExpectedScenarioState
	private TasksListBO tasksListBO;
	@ExpectedScenarioState
	private BoardSnapshot boardSnapshot;
	
	@ProvidedScenarioState
	private TasksListSnapshot tasksListSnapshot;
	@ProvidedScenarioState
	private boolean tasksListAlreadyExistExceptionThrown;
	@ProvidedScenarioState
	private String updatedName;
	@ProvidedScenarioState
   private int countOfRemovedList;
	
	public void addTasksList_is_invoked() {
		try{
			tasksListSnapshot = tasksListBO.add(name, boardSnapshot.getId());
		}catch(TasksListAlreadyExistException e){
			tasksListAlreadyExistExceptionThrown = true;
		}
	}

	public void rename_is_invoked() {
		rename_is_invoked_with_name(CLAZZ);
	}

	public void rename_is_invoked_with_name(String name) {
		updatedName = name;
		
		try {
			tasksListSnapshot = tasksListBO.rename(tasksListSnapshot.getId(), updatedName);
		} catch (TasksListAlreadyExistException e) {
			tasksListAlreadyExistExceptionThrown = true;
		}
	}

	public void delete_is_invoked() {
		tasksListBO.delete(tasksListSnapshot.getId());
	}

   public void delete_by_boardId_invoked() {
      countOfRemovedList = tasksListBO.removeTasksListsWithBoardId(boardSnapshot.getId());
   }

}
