package pl.pszczolkowski.mustdo.domain.task.domain;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.junit.ScenarioTest;

import pl.pszczolkowski.mustdo.Application;
import pl.pszczolkowski.mustdo.domain.board.repository.BoardRepository;
import pl.pszczolkowski.mustdo.domain.task.domain.steps.GivenTasksList;
import pl.pszczolkowski.mustdo.domain.task.domain.steps.ThenTasksList;
import pl.pszczolkowski.mustdo.domain.task.domain.steps.WhenTasksList;
import pl.pszczolkowski.mustdo.domain.task.repository.TasksListRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class TasksListTest extends ScenarioTest<GivenTasksList, WhenTasksList, ThenTasksList>{

private static final String CLAZZ = TasksListTest.class.getSimpleName();
	
	@Autowired
	@ProvidedScenarioState
	private BoardRepository boardRepository;
	@Autowired
	@ProvidedScenarioState
	private TasksListRepository tasksListRepository;
	
	@After
	public void tearDown(){
		tasksListRepository.deleteAll();
		boardRepository.deleteAll();
	}
	
	@Test
	public void should_throw_EntityInStateNewException_when_toSnapshot_invoked_and_entity_is_not_persisted_yet(){
		given().a_board()
			.and().a_not_persisted_tasks_list();
		when().to_snapshot_is_invoked();
		then().EntityInStateNewException_should_be_thrown();
	}
	
	@Test
	public void should_return_snapshot_when_to_Snapshot_invoked(){
		given().a_board()
			.and().a_persistet_tasks_list();
		when().to_snapshot_is_invoked();
		then().snapshot_should_be_returned();
	}
	
	@Test
	public void should_rename_tasks_list_when_rename_invoked(){
		given().a_board()
			.and().a_persisted_tasks_list_with_name(CLAZZ);
		when().rename_is_invoked();
		then().board_should_be_renamed();
	}
	
}
