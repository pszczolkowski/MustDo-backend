package pl.pszczolkowski.mustdo.web.restapi.task.steps;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;

import pl.pszczolkowski.mustdo.domain.board.dto.BoardSnapshot;
import pl.pszczolkowski.mustdo.domain.task.dto.TasksListSnapshot;
import pl.pszczolkowski.mustdo.domain.task.finder.TasksListSnapshotFinder;

public class ThenTasksListDeleteEndpoint extends Stage<ThenTasksListDeleteEndpoint>{
	
	@ExpectedScenarioState
	private TasksListSnapshotFinder tasksListSnapshotFinder;
	@ExpectedScenarioState
	private BoardSnapshot boardSnapshot;
	
	public void tasksList_should_be_deleted() {
		List<TasksListSnapshot> tasksListSnapshots = tasksListSnapshotFinder.findAllWithBoardId(boardSnapshot.getId());
		assertThat(tasksListSnapshots.size(), is(equalTo(0)));
	}

}
