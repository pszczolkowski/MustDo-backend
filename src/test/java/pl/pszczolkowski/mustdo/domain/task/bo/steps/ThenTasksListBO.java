package pl.pszczolkowski.mustdo.domain.task.bo.steps;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.util.Assert.notNull;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;

import pl.pszczolkowski.mustdo.domain.board.dto.BoardSnapshot;
import pl.pszczolkowski.mustdo.domain.task.dto.TasksListSnapshot;
import pl.pszczolkowski.mustdo.domain.task.finder.TasksListSnapshotFinder;

public class ThenTasksListBO extends Stage<ThenTasksListBO>{
	
	@ExpectedScenarioState
	private BoardSnapshot boardSnapshot;
	@ExpectedScenarioState
	private String name;
	@ExpectedScenarioState
	private String updatedName;
	@ExpectedScenarioState
	private TasksListSnapshot tasksListSnapshot;
	@ExpectedScenarioState
	private boolean tasksListAlreadyExistExceptionThrown;
	@ExpectedScenarioState
	private TasksListSnapshotFinder tasksListSnapshotFinder;
	
	public void tasksList_should_be_added() {
		notNull(tasksListSnapshot);
		notNull(tasksListSnapshot.getId());
		assertThat(tasksListSnapshot.getName(), is(equalTo(name)));
		assertThat(tasksListSnapshot.getBoardId(), is(equalTo(boardSnapshot.getId())));
	}

	public void should_throw_tasksListAlreadyExistException() {
		assertTrue(tasksListAlreadyExistExceptionThrown);
	}

	public void tasksList_should_be_renamed() {
		notNull(tasksListSnapshot);
		notNull(tasksListSnapshot.getId());
		assertThat(tasksListSnapshot.getBoardId(), is(equalTo(boardSnapshot.getId())));
		assertThat(tasksListSnapshot.getName(), is(equalTo(updatedName)));
		
	}

	public void board_should_be_removed() {
		TasksListSnapshot tasksListSnapshot = tasksListSnapshotFinder.findOneById(this.tasksListSnapshot.getId());
		assertThat(tasksListSnapshot, is(nullValue()));
	}

	public void nothing_has_changed() {
		notNull(tasksListSnapshot);
		notNull(tasksListSnapshot.getId());
		assertThat(tasksListSnapshot.getBoardId(), is(equalTo(boardSnapshot.getId())));
		assertThat(tasksListSnapshot.getName(), is(equalTo(updatedName)));
		
	}

}
