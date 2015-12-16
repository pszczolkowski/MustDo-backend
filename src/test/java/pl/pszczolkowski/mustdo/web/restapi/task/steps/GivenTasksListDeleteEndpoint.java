package pl.pszczolkowski.mustdo.web.restapi.task.steps;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import pl.pszczolkowski.mustdo.domain.board.bo.BoardBO;
import pl.pszczolkowski.mustdo.domain.board.dto.BoardSnapshot;
import pl.pszczolkowski.mustdo.domain.task.bo.TasksListBO;
import pl.pszczolkowski.mustdo.domain.task.dto.TasksListSnapshot;
import pl.pszczolkowski.mustdo.domain.team.bo.TeamBO;
import pl.pszczolkowski.mustdo.domain.team.dto.TeamSnapshot;

public class GivenTasksListDeleteEndpoint extends Stage<GivenTasksListDeleteEndpoint>{
	
	private static final String CLAZZ = GivenTasksListDeleteEndpoint.class.getSimpleName();

	@ExpectedScenarioState
	private BoardBO boardBO;
	@ExpectedScenarioState
	private TasksListBO tasksListBO;
	@ExpectedScenarioState
	private TeamBO teamBO;
	
	@ProvidedScenarioState
	private BoardSnapshot boardSnapshot;
	@ProvidedScenarioState
	private MockHttpServletRequestBuilder request;

	private TasksListSnapshot tasksListSnapshot;
	private Long id;
	
	public GivenTasksListDeleteEndpoint a_board() {
		TeamSnapshot teamSnapshot = teamBO.add("TEam", 1l);
		boardSnapshot = boardBO.add(CLAZZ,teamSnapshot.getId());
		return this;
	}

	public GivenTasksListDeleteEndpoint a_tasksList() {
		tasksListSnapshot = tasksListBO.add(CLAZZ, boardSnapshot.getId());
		id =  tasksListSnapshot.getId();
		return this;
	}

	public void request_to_endpoint() {
		request = delete("/list/{listId}", id)
							.accept(MediaType.parseMediaType("application/json;charset=UTF-8"));
		
	}

	public GivenTasksListDeleteEndpoint an_invalid_id() {
		id = 9999999999999999L;
		return this;
	}

}
