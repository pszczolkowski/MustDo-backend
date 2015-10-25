package pl.pszczolkowski.mustdo.web.restapi.board.steps;

import org.springframework.test.web.servlet.ResultActions;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;

import pl.pszczolkowski.mustdo.domain.board.dto.BoardSnapshot;
import pl.pszczolkowski.mustdo.domain.board.finder.BoardSnapshotFinder;
import pl.pszczolkowski.mustdo.web.restapi.board.BoardRename;

public class ThenBoardRenameEndpoint extends Stage<ThenBoardRenameEndpoint> {

	@ExpectedScenarioState
	private ResultActions result;
	@ExpectedScenarioState
	private BoardRename boardRename;
	@ExpectedScenarioState
	private BoardSnapshotFinder boardSnapshotFinder;

	public ThenBoardRenameEndpoint board_should_be_renamed() throws Exception {
		BoardSnapshot boardSnapshot= boardSnapshotFinder.findOneByName(boardRename.getName());
		assertNotNull(boardSnapshot);
		return this;
	}
	
	public ThenBoardRenameEndpoint should_return_bad_request() throws Exception{
		result
			.andExpect(status().isBadRequest());
		return this;
	}

	public void renamed_board_should_be_returned() throws Exception {
		result
			.andExpect(status().isOk())
			.andExpect(content().contentType("application/json;charset=UTF-8"))
			.andExpect(jsonPath("$.id", isA(Integer.class)))
			.andExpect(jsonPath("$.name", isA(String.class)))
			.andExpect(jsonPath("$.name", is(equalTo(boardRename.getName()))))
			.andExpect(jsonPath("$.id", is(equalTo(boardRename.getId().intValue()))));
	}

}
