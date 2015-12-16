package pl.pszczolkowski.mustdo.web.restapi.board.steps;

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
import pl.pszczolkowski.mustdo.domain.board.finder.BoardSnapshotFinder;
import pl.pszczolkowski.mustdo.web.restapi.board.BoardNew;

public class ThenBoardAddEndpoint extends Stage<ThenBoardAddEndpoint>{
	
	@ExpectedScenarioState
	private BoardSnapshotFinder boardSnapshotFinder;
	@ExpectedScenarioState
	private BoardNew boardNew;
	@ExpectedScenarioState
	private ResultActions result;
	@ExpectedScenarioState
	private String name;
	
	public ThenBoardAddEndpoint board_should_be_created() {
		BoardSnapshot boardSnapshot = boardSnapshotFinder.findOneByName(boardNew.getName());
		assertNotNull(boardSnapshot);
		return this;
	}

	public ThenBoardAddEndpoint created_board_should_be_returned() throws Exception {
		result
			.andExpect(status().isOk())
			.andExpect(content().contentType("application/json;charset=UTF-8"))
			.andExpect(jsonPath("$.id", isA(Integer.class)))
			.andExpect(jsonPath("$.name", isA(String.class)))
			.andExpect(jsonPath("$.name", is(equalTo(boardNew.getName()))));
		
		return this;
		
	}

	public ThenBoardAddEndpoint should_return_bad_request() throws Exception {
		result
			.andExpect(status().isBadRequest());
		return this;
	}
}
