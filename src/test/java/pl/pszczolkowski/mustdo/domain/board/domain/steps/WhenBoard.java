package pl.pszczolkowski.mustdo.domain.board.domain.steps;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import pl.pszczolkowski.mustdo.domain.board.dto.BoardSnapshot;
import pl.pszczolkowski.mustdo.domain.board.entity.Board;
import pl.pszczolkowski.mustdo.sharedkernel.exception.EntityInStateNewException;

public class WhenBoard extends Stage<WhenBoard> {
	
	@ExpectedScenarioState
	private Board board;
	
	@ProvidedScenarioState
	private BoardSnapshot boardSnapshot;
	@ProvidedScenarioState
	private String name = WhenBoard.class.getSimpleName();
	@ProvidedScenarioState
	private boolean entityInStateNewExceptionWasThrown;
	
	public void to_snapshot_is_invoked(){
		try{
			boardSnapshot = board.toSnapshot();
		}catch(EntityInStateNewException e){
			entityInStateNewExceptionWasThrown = true;
		}
	}

	public void rename_is_invoked() {
		board.rename(name);
		boardSnapshot = board.toSnapshot();
	}
	
}
