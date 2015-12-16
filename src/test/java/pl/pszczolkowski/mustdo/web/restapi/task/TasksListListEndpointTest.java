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
import pl.pszczolkowski.mustdo.web.restapi.task.steps.GivenTasksListListEndpoint;
import pl.pszczolkowski.mustdo.web.restapi.task.steps.ThenTasksListListEndpoint;
import pl.pszczolkowski.mustdo.web.restapi.util.RestApiWhenStage;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class TasksListListEndpointTest
		extends ScenarioTest<GivenTasksListListEndpoint, RestApiWhenStage, ThenTasksListListEndpoint> {
	@Autowired
	@ProvidedScenarioState
	private BoardRepository boardRepository;
	@Autowired
	@ProvidedScenarioState
	private TasksListRepository tasksListRepository;
	@Autowired
	@ProvidedScenarioState
	private BoardBO boardBO;
	@Autowired
	@ProvidedScenarioState
	TasksListBO tasksListBO;
	@Autowired
	@ProvidedScenarioState
	private TasksListSnapshotFinder tasksListSnapshotFinder;
	@Autowired
	@ProvidedScenarioState
	private WebApplicationContext context;
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
	public void should_return_all_tasksList_when_list_invoked() throws Exception{
		given().a_board()
			.and().a_list_of_tasksList()
			.and().a_request_to_endpoint();
		when().request_is_invoked();
		then().all_tasksList_should_be_returned();
	}
}
