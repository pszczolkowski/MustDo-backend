package pl.pszczolkowski.mustdo.web.restapi.task.steps;

import static org.hamcrest.Matchers.isA;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.ResultActions;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;

import pl.pszczolkowski.mustdo.domain.board.dto.BoardSnapshot;
import pl.pszczolkowski.mustdo.domain.task.dto.TasksListSnapshot;
import pl.pszczolkowski.mustdo.domain.task.finder.TasksListSnapshotFinder;

public class ThenTasksListAddEndpoint extends Stage<ThenTasksListAddEndpoint> {

	@ExpectedScenarioState
	private ResultActions result;
	@ExpectedScenarioState
	private TasksListSnapshotFinder tasksListSnapshotFinder;
	@ExpectedScenarioState
	private String name;
	@ExpectedScenarioState
	private BoardSnapshot boardSnapshot;

	public ThenTasksListAddEndpoint tasksList_should_be_created() {
		TasksListSnapshot tasksListSnapshot = tasksListSnapshotFinder.findOneWithNameAndBoardId(name, boardSnapshot.getId());
		assertNotNull(tasksListSnapshot);
		return this;
	}

	public ThenTasksListAddEndpoint created_tasksList_should_be_returned() throws Exception {
		result
			.andExpect(content().contentType("application/json;charset=UTF-8"))
			.andExpect(jsonPath("$.id", isA(Integer.class)))
			.andExpect(jsonPath("$.boardId", isA(Integer.class)))
			.andExpect(jsonPath("$.name", isA(String.class)));

		return this;

	}

	public ThenTasksListAddEndpoint should_return_bad_request() throws Exception {
		result.andExpect(status().isBadRequest());
		return this;
	}

}
