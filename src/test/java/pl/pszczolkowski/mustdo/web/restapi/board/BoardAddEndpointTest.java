package pl.pszczolkowski.mustdo.web.restapi.board;

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
import pl.pszczolkowski.mustdo.domain.board.finder.BoardSnapshotFinder;
import pl.pszczolkowski.mustdo.domain.board.repository.BoardRepository;
import pl.pszczolkowski.mustdo.web.restapi.board.steps.GivenBoardAddEndpoint;
import pl.pszczolkowski.mustdo.web.restapi.board.steps.ThenBoardAddEndpoint;
import pl.pszczolkowski.mustdo.web.restapi.util.RestApiWhenStage;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class BoardAddEndpointTest extends ScenarioTest<GivenBoardAddEndpoint, RestApiWhenStage, ThenBoardAddEndpoint>{
	
	private static final String CLAZZ = BoardAddEndpointTest.class.getSimpleName();
	@Autowired
	@ProvidedScenarioState
	private WebApplicationContext context;
	@Autowired
	@ProvidedScenarioState
	private BoardSnapshotFinder boardSnapshotFinder;
	@Autowired
	@ProvidedScenarioState
	private BoardRepository boardRepository;
	@Autowired
	@ProvidedScenarioState
	private BoardBO boardBO;
	
	@After
	public void tearDown(){
		boardRepository.deleteAll();
	}
	
	@Test
	public void should_create_board_when_add_invoked() throws Exception{
		given().a_name_for_board(CLAZZ)
			.and().a_request_to_endpoint();
		when().request_is_invoked();
		then().board_should_be_created()
			.and().created_board_should_be_returned();
	}
	
	@Test
	public void should_return_bad_request_when_add_invoked_and_board_with_the_same_name_exist() throws Exception{
		given().a_board_with_name(CLAZZ)
			.and().a_name_for_board(CLAZZ)
			.and().a_request_to_endpoint();
		when().request_is_invoked();
		then().should_return_bad_request();
	}
}
