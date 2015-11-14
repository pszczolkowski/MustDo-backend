package pl.pszczolkowski.mustdo.web.restapi.task.steps;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

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
import pl.pszczolkowski.mustdo.domain.task.dto.TasksListSnapshot;
import pl.pszczolkowski.mustdo.web.restapi.task.TasksListRename;

public class GivenTasksListRenameEndpoint extends Stage<GivenTasksListRenameEndpoint>{
	
	private static final String CLAZZ = GivenTasksListRenameEndpoint.class.getSimpleName();

	@ExpectedScenarioState
	private BoardBO boardBO;
	@ExpectedScenarioState
	private TasksListBO tasksListBO;
	
	@ProvidedScenarioState
	private BoardSnapshot boardSnapshot;
	@ProvidedScenarioState
	private TasksListSnapshot tasksListSnapshot;
	@ProvidedScenarioState
	private String updatedName;
	@ProvidedScenarioState
	private TasksListRename tasksListRename;
	@ProvidedScenarioState
	private MockHttpServletRequestBuilder request;
	
	public GivenTasksListRenameEndpoint a_board() {
		boardSnapshot = boardBO.add(CLAZZ);
		return this;
	}

	public GivenTasksListRenameEndpoint a_tasksList_with_name(String name) {
		tasksListSnapshot = tasksListBO.add(name, boardSnapshot.getId());
		return this;
	}

	public GivenTasksListRenameEndpoint a_new_name_for_tasksList(String name) {
		this.updatedName = name;
		return this;
	}

	public void a_request_to_endpoint() throws JsonProcessingException {
		tasksListRename = new TasksListRename();
		tasksListRename.setBoardId(boardSnapshot.getId());
		tasksListRename.setName(updatedName);
		tasksListRename.setId(tasksListSnapshot.getId());
		
		aRequestToEndpoint();
	}

	private void aRequestToEndpoint() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		
		String body = objectMapper.writeValueAsString(tasksListRename);
		
		request = put("/list")
			        .contentType(MediaType.APPLICATION_JSON)
			        .content(body)
			        .accept(MediaType.parseMediaType("application/json;charset=UTF-8"));
			   }
}
