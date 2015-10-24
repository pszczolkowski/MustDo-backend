package pl.pszczolkowski.mustdo.web.restapi.board.steps;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.ResultActions;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;

import pl.pszczolkowski.mustdo.domain.board.dto.BoardSnapshot;

public class ThenBoardGetEndpoint extends Stage<ThenBoardGetEndpoint>{
	
	@ExpectedScenarioState
	private ResultActions result;
	@ExpectedScenarioState
	private BoardSnapshot boardSnapshot;
	
	public ThenBoardGetEndpoint board_should_be_returned() throws Exception {
		result
			.andExpect(status().isOk())
			.andExpect(content().contentType("application/json;charset=UTF-8"))
			.andExpect(jsonPath("$.id", isA(Integer.class)))
			.andExpect(jsonPath("$.name", isA(String.class)))
			.andExpect(jsonPath("$.id", is(equalTo(boardSnapshot.getId().intValue()))))
			.andExpect(jsonPath("$.name", is(equalTo(boardSnapshot.getName()))));
		return this;
	}

	public void not_found_should_be_returned() throws Exception {
		result
			.andExpect(status().isNotFound());
		
	}

}
