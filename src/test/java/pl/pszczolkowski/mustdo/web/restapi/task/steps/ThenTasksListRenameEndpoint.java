package pl.pszczolkowski.mustdo.web.restapi.task.steps;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
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
import pl.pszczolkowski.mustdo.web.restapi.task.TasksListRename;

public class ThenTasksListRenameEndpoint extends Stage<ThenTasksListRenameEndpoint> {
	
	@ExpectedScenarioState
	private TasksListSnapshotFinder tasksListSnapshotFinder;
	@ExpectedScenarioState
	private BoardSnapshot boardSnapshot;
	@ExpectedScenarioState
	private String updatedName;
	@ExpectedScenarioState
	private ResultActions result;
	@ExpectedScenarioState
	private TasksListRename tasksListRename;
	
	public ThenTasksListRenameEndpoint tasksList_should_be_renamed() throws Exception {
		TasksListSnapshot tasksListSnapshot = tasksListSnapshotFinder.findOneByNameAndBoardId(updatedName, boardSnapshot.getId());
		assertNotNull(tasksListSnapshot);
		return this;
	}

	public ThenTasksListRenameEndpoint should_return_bad_request() throws Exception {
		result.andExpect(status().isBadRequest());
		return this;
	}

	public void renamed_tasksList_should_be_returned() throws Exception {
		result.andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$.id", isA(Integer.class)))
				.andExpect(jsonPath("$.name", isA(String.class)))
				.andExpect(jsonPath("$.boardId", isA(Integer.class)))
				.andExpect(jsonPath("$.name", is(equalTo(tasksListRename.getName()))))
				.andExpect(jsonPath("$.id", is(equalTo(tasksListRename.getId().intValue()))));
	}

}
