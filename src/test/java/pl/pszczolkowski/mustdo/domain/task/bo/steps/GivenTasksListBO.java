package pl.pszczolkowski.mustdo.domain.task.bo.steps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import pl.pszczolkowski.mustdo.domain.board.bo.BoardBO;
import pl.pszczolkowski.mustdo.domain.board.dto.BoardSnapshot;
import pl.pszczolkowski.mustdo.domain.task.dto.TasksListSnapshot;
import pl.pszczolkowski.mustdo.domain.task.entity.TasksList;
import pl.pszczolkowski.mustdo.domain.task.repository.TasksListRepository;
import pl.pszczolkowski.mustdo.domain.team.bo.TeamBO;
import pl.pszczolkowski.mustdo.domain.team.dto.TeamSnapshot;

public class GivenTasksListBO extends Stage<GivenTasksListBO>{
	
   private static final Long TEAM_ID = 4L;
	private static final String CLAZZ = GivenTasksListBO.class.getSimpleName();

	@ExpectedScenarioState
	private BoardBO boardBO;
	@ExpectedScenarioState
	private TasksListRepository tasksListRepository;
	@ExpectedScenarioState
	private TeamBO teamBO;
	
	
	@ProvidedScenarioState
	private String name;
	@ProvidedScenarioState
	private BoardSnapshot boardSnapshot;
	@ProvidedScenarioState
	private TasksListSnapshot tasksListSnapshot;
	@ProvidedScenarioState
   private List<TasksListSnapshot> tasksListSnapshots = new ArrayList<>();;
	
	
	public GivenTasksListBO a_board() {
		TeamSnapshot teamSnapshot = teamBO.add(CLAZZ, 1l);
		boardSnapshot = boardBO.add(CLAZZ, teamSnapshot.getId());
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

   public void few_tasks_list_with_boardId() {
      List<String> names = new ArrayList<>(Arrays.asList(new String[]{"TO DO", "DONE", "IN PROGRESS", "FROZEN", "REJECTED"}));
      
      for (String name : names) {
         TasksList tasksList = new TasksList(name, boardSnapshot.getId());
         tasksList = tasksListRepository.save(tasksList);
         tasksListSnapshots.add(tasksList.toSnapshot());
      }
   }

	
}
