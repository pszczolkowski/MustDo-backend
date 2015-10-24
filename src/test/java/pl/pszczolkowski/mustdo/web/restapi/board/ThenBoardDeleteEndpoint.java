package pl.pszczolkowski.mustdo.web.restapi.board;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;

import pl.pszczolkowski.mustdo.domain.board.dto.BoardSnapshot;
import pl.pszczolkowski.mustdo.domain.board.finder.BoardSnapshotFinder;

public class ThenBoardDeleteEndpoint extends Stage<ThenBoardDeleteEndpoint>{
	
	@ExpectedScenarioState
	private BoardSnapshotFinder boardSnapshotFinder;
	@ExpectedScenarioState
	private BoardSnapshot boardSnapshot;
	
	public void board_should_be_removed() {
		boardSnapshot = boardSnapshotFinder.findOneByName(boardSnapshot.getName());
		assertThat(boardSnapshot, is(nullValue()));
	}

}
