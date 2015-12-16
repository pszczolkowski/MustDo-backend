package pl.pszczolkowski.mustdo.web.restapi.task.steps;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import pl.pszczolkowski.mustdo.domain.board.bo.BoardBO;
import pl.pszczolkowski.mustdo.domain.board.dto.BoardSnapshot;
import pl.pszczolkowski.mustdo.domain.task.bo.TasksListBO;
import pl.pszczolkowski.mustdo.domain.team.bo.TeamBO;
import pl.pszczolkowski.mustdo.domain.team.dto.TeamSnapshot;
import pl.pszczolkowski.mustdo.web.restapi.task.TasksListNew;

public class GivenTasksListAddEndpoint extends Stage<GivenTasksListAddEndpoint>{
	
	private static final String CLAZZ = GivenTasksListAddEndpoint.class.getSimpleName();

	@ExpectedScenarioState
	private BoardBO boardBO;
	@ExpectedScenarioState
	private TasksListBO tasksListBO;
	@ExpectedScenarioState
	private TeamBO teamBO;
	
	@ProvidedScenarioState
	private BoardSnapshot boardSnapshot;
	@ProvidedScenarioState
	private String name;
	@ProvidedScenarioState
	private MockHttpServletRequestBuilder request;
	@ProvidedScenarioState
	private TasksListNew tasksListNew;
	
	public GivenTasksListAddEndpoint a_board() {
		TeamSnapshot teamSnapshot = teamBO.add("Team", 1l);
		boardSnapshot = boardBO.add(CLAZZ,teamSnapshot.getId());
		return this;
	}

	public GivenTasksListAddEndpoint a_name_for_a_tasksList(String name) {
		this.name = name;
		return this;
	}

	public void a_request_to_endpoint() throws JsonProcessingException {
		tasksListNew = new TasksListNew();
		tasksListNew.setBoardId(boardSnapshot.getId());
		tasksListNew.setName(this.name);
		
		aRequestToEndpoint();
		
	}
	
	private void aRequestToEndpoint() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();

		String body = objectMapper.writeValueAsString(tasksListNew);

		request = post("/list").contentType(MediaType.APPLICATION_JSON).content(body)
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8"));

	}

	public GivenTasksListAddEndpoint a_tasksList_with_name(String name) {
		tasksListBO.add(name, boardSnapshot.getId());
		return this;
	}

}
