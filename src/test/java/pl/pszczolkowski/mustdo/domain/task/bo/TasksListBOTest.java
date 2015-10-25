package pl.pszczolkowski.mustdo.domain.task.bo;

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
import pl.pszczolkowski.mustdo.domain.board.bo.BoardBO;
import pl.pszczolkowski.mustdo.domain.board.repository.BoardRepository;
import pl.pszczolkowski.mustdo.domain.task.bo.steps.GivenTasksListBO;
import pl.pszczolkowski.mustdo.domain.task.bo.steps.ThenTasksListBO;
import pl.pszczolkowski.mustdo.domain.task.bo.steps.WhenTasksListBO;
import pl.pszczolkowski.mustdo.domain.task.finder.TasksListSnapshotFinder;
import pl.pszczolkowski.mustdo.domain.task.repository.TasksListRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class TasksListBOTest extends ScenarioTest<GivenTasksListBO, WhenTasksListBO, ThenTasksListBO> {
	private static final String CLAZZ = BoardBO.class.getSimpleName();

	@Autowired
	@ProvidedScenarioState
	private BoardBO boardBO;
	@Autowired
	@ProvidedScenarioState
	private TasksListBO tasksListBo;
	@Autowired
	@ProvidedScenarioState
	private BoardRepository boardRepository;
	@Autowired
	@ProvidedScenarioState
	private TasksListRepository tasksListRepository;
	@Autowired
	@ProvidedScenarioState
	private TasksListSnapshotFinder tasksListSnapshotFinder;
	
	@After
	public void tearDown(){
		tasksListRepository.deleteAll();
		boardRepository.deleteAll();
	}
	
	@Test
	public void should_add_new_tasksList(){
		given().a_board()
			.and().a_name_for_tasksList(CLAZZ);
		when().addTasksList_is_invoked();
		then().tasksList_should_be_added();
	}
	
	@Test
	public void should_throw_tasksListAlreadyExistException_when_add_invoked_and_tasksList_already_exist(){
		given().a_board()
			.and().a_tasksList_with_name(CLAZZ)
			.and().a_name_for_tasksList(CLAZZ);
		when().addTasksList_is_invoked();
		then().should_throw_tasksListAlreadyExistException();
	}
	
	@Test
	public void should_rename_existing_tasksList(){
		given().a_board()
			.and().a_tasksList_with_name(CLAZZ);
		when().rename_is_invoked();
		then().tasksList_should_be_renamed();
	}
	
	@Test
	public void should_throw_tasksListAlreadyExistException_when_rename_invoked_and_tasksList_already_exist(){
		given().a_board()
			.and().a_tasksList_with_name(CLAZZ)
			.and().other_tasksList_with_name(CLAZZ+"other");
		when().rename_is_invoked_with_name(CLAZZ);
		then().should_throw_tasksListAlreadyExistException();
	}
	
	@Test
	public void should_return_unchanged_tasksList_when_rename_invoked_and_name_did_not_changed(){
		given().a_board()
			.and().a_tasksList_with_name(CLAZZ);
		when().rename_is_invoked_with_name(CLAZZ);
		then().nothing_should_have_changed();
	}
	
	@Test
	public void should_delete_tasksList(){
		given().a_board()
			.and().a_tasksList_with_name(CLAZZ);
		when().delete_is_invoked();
		then().board_should_be_removed();
	}
}
