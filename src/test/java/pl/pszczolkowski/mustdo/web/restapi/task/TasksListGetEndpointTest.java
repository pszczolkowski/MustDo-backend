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
import pl.pszczolkowski.mustdo.domain.task.repository.TasksListRepository;
import pl.pszczolkowski.mustdo.web.restapi.task.steps.GivenTasksListGetEndpoint;
import pl.pszczolkowski.mustdo.web.restapi.task.steps.ThenTasksListGetEndpoint;
import pl.pszczolkowski.mustdo.web.restapi.util.RestApiWhenStage;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class TasksListGetEndpointTest extends ScenarioTest<GivenTasksListGetEndpoint, RestApiWhenStage, ThenTasksListGetEndpoint>{
	
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
	
	@After
	public void tearDown(){
		tasksListRepository.deleteAll();
		boardRepository.deleteAll();
	}
	
	@Test
	public void should_return_tasks_list_when_get_invoked() throws Exception{
		given().a_board()
			.and().a_tasksList()
			.and().a_request_to_endpoint();
		when().request_is_invoked();
		then().tasksList_should_be_returned();
	}
	
	@Test
	public void should_return_not_found_when_get_invoked_with_invalid_id() throws Exception{
		given().an_invalid_id()
			.and().a_request_to_endpoint();
		when().request_is_invoked();
		then().not_found_should_be_returned();
	}

}
