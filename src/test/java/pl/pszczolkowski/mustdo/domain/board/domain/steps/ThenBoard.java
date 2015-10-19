package pl.pszczolkowski.mustdo.domain.board.domain.steps;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;

import pl.pszczolkowski.mustdo.domain.board.dto.BoardSnapshot;
import pl.pszczolkowski.mustdo.domain.board.repository.BoardRepository;

public class ThenBoard extends Stage<ThenBoard> {

	@ExpectedScenarioState
	private boolean entityInStateNewExceptionWasThrown;
	@ExpectedScenarioState
	private BoardSnapshot boardSnapshot;
	@ExpectedScenarioState
	private String name;
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
	}

}
