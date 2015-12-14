package pl.pszczolkowski.mustdo.domain.board.bo.steps;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import pl.pszczolkowski.mustdo.domain.board.dto.BoardSnapshot;
import pl.pszczolkowski.mustdo.domain.board.entity.Board;
import pl.pszczolkowski.mustdo.domain.board.repository.BoardRepository;
import pl.pszczolkowski.mustdo.domain.team.bo.TeamBO;
import pl.pszczolkowski.mustdo.domain.team.dto.TeamSnapshot;

public class GivenBoardBO extends Stage<GivenBoardBO>{
	
	@ProvidedScenarioState
	private TeamSnapshot teamSnapshot;
	
	private static final String CLAZZ = GivenBoardBO.class.getSimpleName();
	@ProvidedScenarioState
	private String name;
	@ProvidedScenarioState
	private Long teamId;
	@ProvidedScenarioState
	private BoardSnapshot boardSnapshot;
	
	@ExpectedScenarioState
	private BoardRepository boardRepository;
	@ExpectedScenarioState
	private TeamBO teamBO;
	
	public GivenBoardBO a_name_for_board(String name) {
		this.name = name;
		return this;
	}
	
	public GivenBoardBO a_board_with_name_and_teamID(String name, Long teamId) {
		Board board = new Board(name, teamSnapshot.getId());
		board = boardRepository.save(board);
		boardSnapshot = board.toSnapshot();
		return this;
		
	}

	public GivenBoardBO other_board_with_name_and_team_id(String name, Long teamId) {
		Board board = new Board(name, teamSnapshot.getId());
		board = boardRepository.save(board);
		return this;
	}

   public GivenBoardBO a_team_id_for_board(Long teamId) {
      this.teamId = teamId;
      return this;
   }

	public GivenBoardBO a_team() {
		teamSnapshot = teamBO.add(CLAZZ, 1l);
		return this;
	}

}
