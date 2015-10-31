package pl.pszczolkowski.mustdo.domain.task.bo.steps;

import java.util.List;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;

import pl.pszczolkowski.mustdo.domain.board.dto.BoardSnapshot;
import pl.pszczolkowski.mustdo.domain.task.dto.TasksListSnapshot;
import pl.pszczolkowski.mustdo.domain.task.finder.TasksListSnapshotFinder;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.util.Assert.notNull;

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
	@ExpectedScenarioState
   private int countOfRemovedList;
	@ExpectedScenarioState
   private List<TasksListSnapshot> tasksListSnapshots;
	
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

	public void nothing_should_have_changed() {
		notNull(tasksListSnapshot);
		notNull(tasksListSnapshot.getId());
		assertThat(tasksListSnapshot.getBoardId(), is(equalTo(boardSnapshot.getId())));
		assertThat(tasksListSnapshot.getName(), is(equalTo(updatedName)));
		
	}

   public void tasksLists_should_be_deleted() {
      assertThat(countOfRemovedList, is(equalTo(tasksListSnapshots.size())));
      tasksListSnapshots.stream().forEach((tasksListSnapshot) -> {
         assertThat(tasksListSnapshotFinder.findOneById(tasksListSnapshot.getId()), is(nullValue()));
      });
   }

}
