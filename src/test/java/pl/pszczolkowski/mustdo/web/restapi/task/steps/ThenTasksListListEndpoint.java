package pl.pszczolkowski.mustdo.web.restapi.task.steps;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isA;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.ResultActions;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;

import pl.pszczolkowski.mustdo.domain.board.dto.BoardSnapshot;
import pl.pszczolkowski.mustdo.domain.task.finder.TasksListSnapshotFinder;

public class ThenTasksListListEndpoint extends Stage<ThenTasksListListEndpoint>{
	
	@ExpectedScenarioState
	private TasksListSnapshotFinder tasksListSnapshotFinder;
	@ExpectedScenarioState
	private BoardSnapshot boardSnapshot;
	@ExpectedScenarioState
	private ResultActions result;
	
	@SuppressWarnings("unchecked")
	public void all_tasksList_should_be_returned() throws Exception {
		int expectedSize = tasksListSnapshotFinder.findAllWithBoardId(boardSnapshot.getId()).size();
		result
			.andExpect(status().isOk())
			.andExpect(content().contentType("application/json;charset=UTF-8"))
			.andExpect(jsonPath("$").isArray())
			.andExpect(jsonPath("$", hasSize(expectedSize)))
			.andExpect(jsonPath("$[*].id", hasItems(isA(Integer.class))))
			.andExpect(jsonPath("$[*].name", hasItems(isA(String.class))))
			.andExpect(jsonPath("$[*].boardId", hasItems(isA(Integer.class))));
	}

}
