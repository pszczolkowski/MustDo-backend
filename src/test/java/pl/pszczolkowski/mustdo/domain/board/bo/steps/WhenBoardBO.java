package pl.pszczolkowski.mustdo.domain.board.bo.steps;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.annotation.ScenarioState;

import pl.pszczolkowski.mustdo.domain.board.bo.BoardBO;
import pl.pszczolkowski.mustdo.domain.board.dto.BoardSnapshot;
import pl.pszczolkowski.mustdo.domain.board.exception.BoardAlreadyExistException;
import pl.pszczolkowski.mustdo.domain.team.dto.TeamSnapshot;

public class WhenBoardBO extends Stage<WhenBoardBO>{
	
	private static final String CLAZZ = WhenBoardBO.class.getSimpleName();

	@ScenarioState
	private BoardSnapshot boardSnapshot;
	
	@ProvidedScenarioState
	private boolean boardAlreadyExistExceptionThrown = false;
	@ProvidedScenarioState
	private String updatedName;
	
	@ExpectedScenarioState
	private BoardBO boardBO;
	@ExpectedScenarioState
	private String name;
	@ExpectedScenarioState
	private TeamSnapshot teamSnapshot;
	
	
	public void addBoard_is_invoked() {
		try{
			boardSnapshot = boardBO.add(name, teamSnapshot.getId());		
		}catch(BoardAlreadyExistException e){
			boardAlreadyExistExceptionThrown = true;
		}
	}

	public void rename_is_invoked_with_name(String name) {
		updatedName = name;
		
		try {
			boardSnapshot = boardBO.rename(boardSnapshot.getId(), updatedName);
		} catch (BoardAlreadyExistException e) {
			boardAlreadyExistExceptionThrown = true;
		}
	}
	
	public void rename_is_invoked() {
		rename_is_invoked_with_name(CLAZZ);
	}

	public void delete_is_invoked() {
		boardBO.delete(boardSnapshot.getId());
	}

}
