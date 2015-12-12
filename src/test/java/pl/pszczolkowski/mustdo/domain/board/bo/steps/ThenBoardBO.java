package pl.pszczolkowski.mustdo.domain.board.bo.steps;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import pl.pszczolkowski.mustdo.domain.board.dto.BoardSnapshot;
import pl.pszczolkowski.mustdo.domain.board.entity.Board;
import pl.pszczolkowski.mustdo.domain.board.repository.BoardRepository;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class ThenBoardBO extends Stage<ThenBoardBO> {
	@ExpectedScenarioState
	private BoardSnapshot boardSnapshot;
	@ExpectedScenarioState
	private BoardRepository boardRepository;
	@ExpectedScenarioState
	private String name;
	@ExpectedScenarioState
	private Long teamId;
	@ExpectedScenarioState
	private boolean boardAlreadyExistExceptionThrown;
	@ProvidedScenarioState
	private String updatedName;
	
	public void board_should_be_added() {
		assertNotNull(boardSnapshot);
		assertThat(boardSnapshot.getName(), is(equalTo(name)));
		assertThat(boardSnapshot.getTeamId(), is(equalTo(teamId)));
	}

	public void boardAlreadyExistException_should_be_thrown() {
		assertTrue(boardAlreadyExistExceptionThrown);
	}

	public void board_should_be_renamed() {
		assertNotNull(boardSnapshot);
		assertThat(boardSnapshot.getName(), is(equalTo(updatedName)));

	}

	public void board_should_be_removed() {
		Board findOne = boardRepository.findOne(boardSnapshot.getId());
		assertThat(findOne, is(nullValue()));
	}

	public void nothing_should_have_changed() {
		assertThat(boardSnapshot.getName(), is(equalTo(updatedName)));
	}

}
