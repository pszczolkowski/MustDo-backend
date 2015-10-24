package pl.pszczolkowski.mustdo.domain.board.bo.steps;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import pl.pszczolkowski.mustdo.domain.board.dto.BoardSnapshot;
import pl.pszczolkowski.mustdo.domain.board.entity.Board;
import pl.pszczolkowski.mustdo.domain.board.repository.BoardRepository;

public class ThenBoardBO extends Stage<ThenBoardBO> {
	@ExpectedScenarioState
	private BoardSnapshot boardSnapshot;
	@ExpectedScenarioState
	private BoardRepository boardRepository;
	@ExpectedScenarioState
	private String name;
	@ExpectedScenarioState
	private boolean boardAlreadyExistExceptionThrown;
	@ProvidedScenarioState
	private String updatedName;
	
	public void board_should_be_added() {
		assertNotNull(boardSnapshot);
		assertThat(boardSnapshot.getName(), is(equalTo(name)));
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

	public void nothing_has_changed() {
		assertThat(boardSnapshot.getName(), is(equalTo(updatedName)));
	}

}
