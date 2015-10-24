package pl.pszczolkowski.mustdo.domain.task.domain.steps;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Assert;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import pl.pszczolkowski.mustdo.domain.board.dto.BoardSnapshot;
import pl.pszczolkowski.mustdo.domain.task.dto.TasksListSnapshot;

public class ThenTasksList extends Stage<ThenTasksList>{
	
	@ProvidedScenarioState
	private boolean entityInStateNewWasThrown;
	@ProvidedScenarioState
	private TasksListSnapshot tasksListSnapshot;
	@ProvidedScenarioState
	private BoardSnapshot boardSnapshot;
	@ProvidedScenarioState
	private String name;
	@ProvidedScenarioState
	private String newName;
	
	@SuppressWarnings("deprecation")
	public void EntityInStateNewException_should_be_thrown() {
		assertTrue(entityInStateNewWasThrown);
	}

	public void snapshot_should_be_returned() {
		assertNotNull(tasksListSnapshot);
		assertNotNull(tasksListSnapshot.getId());
		assertThat(tasksListSnapshot.getName(), is(equalTo(name)));
		assertThat(tasksListSnapshot.getBoardId(), is(equalTo(boardSnapshot.getId())));
		
	}

	public void board_should_be_renamed() {
		assertNotNull(tasksListSnapshot);
		assertNotNull(tasksListSnapshot.getId());
		assertThat(tasksListSnapshot.getName(), is(equalTo(newName)));
		assertThat(tasksListSnapshot.getBoardId(), is(equalTo(boardSnapshot.getId())));
	}

}
