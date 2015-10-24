package pl.pszczolkowski.mustdo.domain.task.bo.steps;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import pl.pszczolkowski.mustdo.domain.board.bo.BoardBO;
import pl.pszczolkowski.mustdo.domain.board.dto.BoardSnapshot;
import pl.pszczolkowski.mustdo.domain.task.dto.TasksListSnapshot;
import pl.pszczolkowski.mustdo.domain.task.entity.TasksList;
import pl.pszczolkowski.mustdo.domain.task.repository.TasksListRepository;

public class GivenTasksListBO extends Stage<GivenTasksListBO>{
	
	private static final String CLAZZ = GivenTasksListBO.class.getSimpleName();

	@ExpectedScenarioState
	private BoardBO boardBO;
	@ExpectedScenarioState
	private TasksListRepository tasksListRepository;
	
	
	@ProvidedScenarioState
	private String name;
	@ProvidedScenarioState
	private BoardSnapshot boardSnapshot;
	@ProvidedScenarioState
	private TasksListSnapshot tasksListSnapshot;
	
	public GivenTasksListBO a_board() {
		boardSnapshot = boardBO.add(CLAZZ);
		return this;
	}

	public GivenTasksListBO a_name_for_tasksList(String name) {
		this.name = name;
		return this;
	}

	public GivenTasksListBO a_tasksList_with_name(String name) {
		TasksList tasksList = new TasksList(name, boardSnapshot.getId());
		tasksList = tasksListRepository.save(tasksList);
		tasksListSnapshot = tasksList.toSnapshot();
		return this;
	}

	public GivenTasksListBO other_tasksList_with_name(String name) {
		TasksList tasksList = new TasksList(name, boardSnapshot.getId());
		tasksListRepository.save(tasksList);
		tasksListSnapshot = tasksList.toSnapshot();
		return this;
		
	}

	
}
