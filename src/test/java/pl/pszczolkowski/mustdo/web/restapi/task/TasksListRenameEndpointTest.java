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
import pl.pszczolkowski.mustdo.config.OAuthHelper;
import pl.pszczolkowski.mustdo.domain.board.bo.BoardBO;
import pl.pszczolkowski.mustdo.domain.board.repository.BoardRepository;
import pl.pszczolkowski.mustdo.domain.task.bo.TasksListBO;
import pl.pszczolkowski.mustdo.domain.task.finder.TasksListSnapshotFinder;
import pl.pszczolkowski.mustdo.domain.task.repository.TasksListRepository;
import pl.pszczolkowski.mustdo.domain.team.bo.TeamBO;
import pl.pszczolkowski.mustdo.domain.team.repository.TeamRepository;
import pl.pszczolkowski.mustdo.web.restapi.task.steps.GivenTasksListRenameEndpoint;
import pl.pszczolkowski.mustdo.web.restapi.task.steps.ThenTasksListRenameEndpoint;
import pl.pszczolkowski.mustdo.web.restapi.util.RestApiWhenStage;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class TasksListRenameEndpointTest extends ScenarioTest<GivenTasksListRenameEndpoint, RestApiWhenStage, ThenTasksListRenameEndpoint>{

	private static final String CLAZZ = TasksListRenameEndpointTest.class.getSimpleName();
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
	@Autowired
	@ProvidedScenarioState
	private OAuthHelper oauthHelper;
	@Autowired
	@ProvidedScenarioState
	private TeamBO teamBO;
	@Autowired
	@ProvidedScenarioState
	private TeamRepository teamRepository;
	
	@After
	public void tearDown(){
		tasksListRepository.deleteAll();
		boardRepository.deleteAll();
		teamRepository.deleteAll();
	}
	
	@Test
	public void should_rename_tasksList_when_rename_invoked() throws Exception{
		given().a_board()
			.and().a_tasksList_with_name(CLAZZ)
			.and().a_tasksList_with_name(CLAZZ+"1")
			.and().a_new_name_for_tasksList(CLAZZ+"2")
			.and().a_request_to_endpoint();
		when().request_is_invoked();
		then().tasksList_should_be_renamed()
			.and().renamed_tasksList_should_be_returned();
		
	}
	
	@Test
	public void should_return_bad_request_when_rename_invoked_and_tasksList_with_the_same_name_exist() throws Exception {
		given().a_board()
			.and().a_tasksList_with_name(CLAZZ)
			.and().a_tasksList_with_name(CLAZZ+"1")
			.and().a_new_name_for_tasksList(CLAZZ)
			.and().a_request_to_endpoint();
		when().request_is_invoked();
		then().should_return_bad_request();
	}
	
	
}
