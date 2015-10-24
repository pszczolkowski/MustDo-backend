package pl.pszczolkowski.mustdo.web.restapi.board.steps;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isA;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.ResultActions;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;

import pl.pszczolkowski.mustdo.domain.board.finder.BoardSnapshotFinder;

public class ThenBoardListEndpoint extends Stage<ThenBoardListEndpoint>{
	@ExpectedScenarioState
	private ResultActions result;
	@ExpectedScenarioState
	private BoardSnapshotFinder boardSnapshotFinder;
	
	@SuppressWarnings("unchecked")
	public void all_boards_should_be_returned() throws Exception {
		int expectedSize = boardSnapshotFinder.findAll().size();
		result
			.andExpect(status().isOk())
			.andExpect(content().contentType("application/json;charset=UTF-8"))
			.andExpect(jsonPath("$").isArray())
			.andExpect(jsonPath("$", hasSize(equalTo(expectedSize))))
			.andExpect(jsonPath("$[*].id", hasItems(isA(Integer.class))))
			.andExpect(jsonPath("$[*].name", hasItems(isA(String.class))));
	}
	
}
