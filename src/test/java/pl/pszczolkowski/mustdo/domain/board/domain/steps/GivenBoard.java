package pl.pszczolkowski.mustdo.domain.board.domain.steps;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import pl.pszczolkowski.mustdo.domain.board.entity.Board;
import pl.pszczolkowski.mustdo.domain.board.repository.BoardRepository;

public class GivenBoard extends Stage<GivenBoard>{
	@ProvidedScenarioState
	private Board board;
	@ProvidedScenarioState
	private String name = GivenBoard.class.getSimpleName();
	@ProvidedScenarioState
   private Long teamID = 3L;
	
	@ExpectedScenarioState
	private BoardRepository boardRepository;


	public void a_not_persisted_board() {
		board = new Board(name, teamID);
	}

	public void a_persisted_board() {
		board = new Board(name, teamID);
		boardRepository.save(board);
		
	}

	public GivenBoard a_persisted_board_with_name(String clazz) {
		board = new Board(name, teamID);
		boardRepository.save(board);
		return this;
	}

}