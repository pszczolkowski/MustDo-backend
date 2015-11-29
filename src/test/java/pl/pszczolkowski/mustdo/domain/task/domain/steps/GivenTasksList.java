package pl.pszczolkowski.mustdo.domain.task.domain.steps;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import pl.pszczolkowski.mustdo.domain.board.dto.BoardSnapshot;
import pl.pszczolkowski.mustdo.domain.board.entity.Board;
import pl.pszczolkowski.mustdo.domain.board.repository.BoardRepository;
import pl.pszczolkowski.mustdo.domain.task.entity.TasksList;
import pl.pszczolkowski.mustdo.domain.task.repository.TasksListRepository;

public class GivenTasksList extends Stage<GivenTasksList>{
	
   private static final long TEAM_ID = 2l;
	@ExpectedScenarioState
	private BoardRepository boardRepository;
	@ExpectedScenarioState
	private TasksListRepository tasksListRepository;
	
	@ProvidedScenarioState
	private  String name = GivenTasksList.class.getSimpleName();
	@ProvidedScenarioState
	private BoardSnapshot boardSnapshot;
	@ProvidedScenarioState
	private TasksList tasksList;
	
	public GivenTasksList a_board() {
		Board board = new Board(name, TEAM_ID);
		board = boardRepository.save(board);
		boardSnapshot = board.toSnapshot();
		return this;
	}

	public GivenTasksList a_not_persisted_tasks_list() {
		tasksList = new TasksList(name, boardSnapshot.getId());
		return this;
	}

	public GivenTasksList a_persistet_tasks_list() {
		tasksList = new TasksList(name, boardSnapshot.getId());
		tasksList = tasksListRepository.save(tasksList);
		return this;
		
	}

	public void a_persisted_tasks_list_with_name(String name) {
		tasksList = new TasksList(name, boardSnapshot.getId());
		tasksList = tasksListRepository.save(tasksList);
	}

}
