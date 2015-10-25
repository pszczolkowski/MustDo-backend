package pl.pszczolkowski.mustdo.web.restapi.task;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.junit.ScenarioTest;

import pl.pszczolkowski.mustdo.Application;
import pl.pszczolkowski.mustdo.domain.board.bo.BoardBO;
import pl.pszczolkowski.mustdo.domain.board.repository.BoardRepository;
import pl.pszczolkowski.mustdo.domain.task.bo.TasksListBO;
import pl.pszczolkowski.mustdo.domain.task.finder.TasksListSnapshotFinder;
import pl.pszczolkowski.mustdo.domain.task.repository.TasksListRepository;
import pl.pszczolkowski.mustdo.web.restapi.task.steps.GivenTasksListAddEndpoint;
import pl.pszczolkowski.mustdo.web.restapi.task.steps.ThenTasksListAddEndpoint;
import pl.pszczolkowski.mustdo.web.restapi.util.RestApiWhenStage;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class TasksListAddEndpointTest extends ScenarioTest<GivenTasksListAddEndpoint, RestApiWhenStage, ThenTasksListAddEndpoint>{
	private static final String CLAZZ = TasksListAddEndpointTest.class.getSimpleName();
	@Autowired
	private BoardRepository boardRepository;
	@Autowired
	private TasksListRepository tasksListRepository;
	@Autowired
	@ProvidedScenarioState
	private BoardBO boardBO;
	@Autowired
	@ProvidedScenarioState
	private TasksListBO tasksListBO;
	@Autowired
	@ProvidedScenarioState
	private WebApplicationContext context;
	@Autowired
	@ProvidedScenarioState
	private TasksListSnapshotFinder tasksListSnapshotFinder;
	
	@After
	public void tearDown(){
		tasksListRepository.deleteAll();
		boardRepository.deleteAll();
	}
	
	@Test
	public void should_add_tasksList_when_add_invoked() throws Exception{
		given().a_board()
			.and().a_name_for_a_tasksList(CLAZZ)
			.and().a_request_to_endpoint();
		when().request_is_invoked();
		then().tasksList_should_be_created()
			.and().created_tasksList_should_be_returned();
	}
	
	@Test
	public void should_return_bad_request_when_add_invoked_and_tasksList_with_the_same_name_exist() throws Exception{
		given().a_board()
			.and().a_tasksList_with_name(CLAZZ)
			.and().a_name_for_a_tasksList(CLAZZ)
			.and().a_request_to_endpoint();
		when().request_is_invoked();
		then().should_return_bad_request();
	}
}
