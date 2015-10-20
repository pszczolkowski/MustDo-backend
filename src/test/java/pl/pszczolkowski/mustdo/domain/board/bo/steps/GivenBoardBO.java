package pl.pszczolkowski.mustdo.domain.board.bo.steps;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import pl.pszczolkowski.mustdo.domain.board.dto.BoardSnapshot;
import pl.pszczolkowski.mustdo.domain.board.entity.Board;
import pl.pszczolkowski.mustdo.domain.board.repository.BoardRepository;

public class GivenBoardBO extends Stage<GivenBoardBO>{
	
	@ProvidedScenarioState
	private String name;
	@ProvidedScenarioState
	private BoardSnapshot boardSnapshot;
	
	@ExpectedScenarioState
	private BoardRepository boardRepository;
	
	public GivenBoardBO a_name_for_board(String name) {
		this.name = name;
		return this;
	}
	
	public GivenBoardBO a_board_with_name(String name) {
		Board board = new Board(name);
		board = boardRepository.save(board);
		boardSnapshot = board.toSnapshot();
		return this;
		
	}

	public GivenBoardBO other_board_with_name(String name) {
		Board board = new Board(name);
		board = boardRepository.save(board);
		return this;
	}

}
