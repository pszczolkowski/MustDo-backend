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
import pl.pszczolkowski.mustdo.domain.board.repository.BoardRepository;
import pl.pszczolkowski.mustdo.web.restapi.board.steps.GivenBoardGetEndpoint;
import pl.pszczolkowski.mustdo.web.restapi.board.steps.ThenBoardGetEndpoint;
import pl.pszczolkowski.mustdo.web.restapi.util.RestApiWhenStage;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class BoardGetEndpointTest extends ScenarioTest<GivenBoardGetEndpoint, RestApiWhenStage, ThenBoardGetEndpoint>{
	@Autowired
	@ProvidedScenarioState
	private WebApplicationContext context;
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
	public void should_return_board_when_get_invoked() throws Exception{
		given().a_board()
			.and().a_request_to_endpoint();
		when().request_is_invoked();
		then().board_should_be_returned();
	}
	
	@Test
	public void should_return_not_found_when_board_with_id_does_not_exist() throws Exception{
		given().an_invalid_id()
			.and().a_request_to_endpoint();
		when().request_is_invoked();
		then().not_found_should_be_returned();
	}
}
