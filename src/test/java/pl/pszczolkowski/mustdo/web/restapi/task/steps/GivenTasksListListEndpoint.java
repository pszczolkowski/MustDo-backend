package pl.pszczolkowski.mustdo.web.restapi.task.steps;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import pl.pszczolkowski.mustdo.domain.board.bo.BoardBO;
import pl.pszczolkowski.mustdo.domain.board.dto.BoardSnapshot;
import pl.pszczolkowski.mustdo.domain.task.bo.TasksListBO;
import pl.pszczolkowski.mustdo.domain.team.bo.TeamBO;
import pl.pszczolkowski.mustdo.domain.team.dto.TeamSnapshot;


public class GivenTasksListListEndpoint extends Stage<GivenTasksListListEndpoint>{
	
	private static final String CLAZZ = GivenTasksListListEndpoint.class.getSimpleName();
	@ExpectedScenarioState
	private BoardBO boardBO;
	@ExpectedScenarioState
	private TasksListBO tasksListBO;
	@ExpectedScenarioState
	private TeamBO teamBO;
	
	@ProvidedScenarioState
	private MockHttpServletRequestBuilder request;
	@ProvidedScenarioState
	BoardSnapshot boardSnapshot;
	
	public GivenTasksListListEndpoint a_board() {
		TeamSnapshot teamSnapshot = teamBO.add("team", 1l);
		boardSnapshot = boardBO.add(CLAZZ,teamSnapshot.getId());
		return this;
	}

	public GivenTasksListListEndpoint a_list_of_tasksList() {
		tasksListBO.add(CLAZZ+"3", boardSnapshot.getId());
		tasksListBO.add(CLAZZ+"1", boardSnapshot.getId());
		tasksListBO.add(CLAZZ+"2", boardSnapshot.getId());
		return this;
	}

	public void a_request_to_endpoint() {
		request = get("/list")
				.param("boardId", String.valueOf(boardSnapshot.getId()))
				.accept(MediaType.APPLICATION_JSON);
		
	}

}
