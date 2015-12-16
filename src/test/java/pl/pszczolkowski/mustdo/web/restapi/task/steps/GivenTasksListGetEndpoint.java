package pl.pszczolkowski.mustdo.web.restapi.task.steps;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.annotation.ScenarioState;

import pl.pszczolkowski.mustdo.domain.board.bo.BoardBO;
import pl.pszczolkowski.mustdo.domain.board.dto.BoardSnapshot;
import pl.pszczolkowski.mustdo.domain.task.bo.TasksListBO;
import pl.pszczolkowski.mustdo.domain.task.dto.TasksListSnapshot;
import pl.pszczolkowski.mustdo.domain.team.bo.TeamBO;
import pl.pszczolkowski.mustdo.domain.team.dto.TeamSnapshot;

public class GivenTasksListGetEndpoint extends Stage<GivenTasksListGetEndpoint>{
	
	private static final String CLAZZ = GivenTasksListGetEndpoint.class.getSimpleName();

	@ExpectedScenarioState
	private BoardBO boardBO;
	@ExpectedScenarioState
	private TasksListBO tasksListBO;
	@ExpectedScenarioState
	private TeamBO teamBO;
	@ExpectedScenarioState
	
	@ProvidedScenarioState
	private BoardSnapshot boardSnapshot;
	@ProvidedScenarioState
	private MockHttpServletRequestBuilder request;
	
	TasksListSnapshot tasksListSnapshot;
	@ScenarioState
	Long id;
	
	public GivenTasksListGetEndpoint a_board() {
		TeamSnapshot teamSnapshot = teamBO.add("Team", 1l);
		boardSnapshot = boardBO.add(CLAZZ, teamSnapshot.getId());
		return this;
	}

	public GivenTasksListGetEndpoint a_tasksList() {
		tasksListSnapshot = tasksListBO.add(CLAZZ, boardSnapshot.getId());
		id = tasksListSnapshot.getId();
		return this;
	}

	public GivenTasksListGetEndpoint a_request_to_endpoint() {
		request = get("/list/{listId}", id)
					.accept(MediaType.parseMediaType("application/json;charset=UTF-8"));
		return this;
		
	}

	public GivenTasksListGetEndpoint an_invalid_id() {
		id = 999999999999L;
		return this;
	}

}
