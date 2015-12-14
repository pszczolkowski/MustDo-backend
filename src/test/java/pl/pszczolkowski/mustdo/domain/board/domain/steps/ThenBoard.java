package pl.pszczolkowski.mustdo.domain.board.domain.steps;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;

import pl.pszczolkowski.mustdo.domain.board.dto.BoardSnapshot;
import pl.pszczolkowski.mustdo.domain.board.repository.BoardRepository;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class ThenBoard extends Stage<ThenBoard> {

	@ExpectedScenarioState
	private boolean entityInStateNewExceptionWasThrown;
	@ExpectedScenarioState
	private BoardSnapshot boardSnapshot;
	@ExpectedScenarioState
	private String name;
	@ExpectedScenarioState
	private Long teamID;
	@ExpectedScenarioState
	private BoardRepository boardRepository;

	public void snapshot_should_be_returned() {
		assertNotNull(boardSnapshot);
	}

	public void EntityInStateNewException_should_be_thrown(){
		assertTrue(entityInStateNewExceptionWasThrown);
	}

	public void board_should_be_renamed() {
		assertNotNull(boardSnapshot);
		assertThat(boardSnapshot.getName(), is(equalTo(name)));
		assertThat(boardSnapshot.getTeamId(), is(equalTo(teamID)));
	}

}
