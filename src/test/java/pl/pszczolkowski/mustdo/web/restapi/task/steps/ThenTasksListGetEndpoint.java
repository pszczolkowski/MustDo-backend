package pl.pszczolkowski.mustdo.web.restapi.task.steps;

import static org.hamcrest.Matchers.isA;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.ResultActions;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import pl.pszczolkowski.mustdo.domain.board.dto.BoardSnapshot;

public class ThenTasksListGetEndpoint extends Stage<ThenTasksListGetEndpoint>{
	
	@ExpectedScenarioState
	private BoardSnapshot boardSnapshot;
	@ProvidedScenarioState
	private ResultActions result;
	
	public void tasksList_should_be_returned() throws Exception {
		result
			.andExpect(status().isOk())
			.andExpect(content().contentType("application/json;charset=UTF-8"))
			.andExpect(jsonPath("$.id", isA(Integer.class)))
			.andExpect(jsonPath("$.name", isA(String.class)))
			.andExpect(jsonPath("$.boardId", isA(Integer.class)));
		
		
	}

	public void not_found_should_be_returned() throws Exception {
		result
			.andExpect(status().isNotFound());
		
	}

}
