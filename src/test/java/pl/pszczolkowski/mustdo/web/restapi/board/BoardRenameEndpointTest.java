package pl.pszczolkowski.mustdo.web.restapi.board;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.junit.ScenarioTest;

import pl.pszczolkowski.mustdo.Application;
import pl.pszczolkowski.mustdo.config.OAuthHelper;
import pl.pszczolkowski.mustdo.domain.board.bo.BoardBO;
import pl.pszczolkowski.mustdo.domain.board.finder.BoardSnapshotFinder;
import pl.pszczolkowski.mustdo.domain.board.repository.BoardRepository;
import pl.pszczolkowski.mustdo.domain.team.bo.TeamBO;
import pl.pszczolkowski.mustdo.domain.team.repository.TeamRepository;
import pl.pszczolkowski.mustdo.domain.user.repo.UserRepository;
import pl.pszczolkowski.mustdo.web.restapi.board.steps.GivenBoardRenameEndpoint;
import pl.pszczolkowski.mustdo.web.restapi.board.steps.ThenBoardRenameEndpoint;
import pl.pszczolkowski.mustdo.web.restapi.util.RestApiWhenStage;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class BoardRenameEndpointTest extends ScenarioTest<GivenBoardRenameEndpoint, RestApiWhenStage, ThenBoardRenameEndpoint>{
	private static final String CLAZZ = BoardRenameEndpointTest.class.getSimpleName();
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
	@Autowired
	@ProvidedScenarioState
	private OAuthHelper oauthHelper;
	@Autowired
	@ProvidedScenarioState
	private TeamBO teamBO;
	@Autowired
	@ProvidedScenarioState
	private TeamRepository teamRepository;
	@Autowired
	@ProvidedScenarioState
	private UserRepository userRepository;
	
	@After
	public void tearDown(){
		boardRepository.deleteAll();
		teamRepository.deleteAll();
		userRepository.deleteAll();
	}
	
	@Test
	@WithMockUser(username = "user")
	public void should_rename_board_when_rename_invoked() throws Exception{
		given().an_user_with_name("user").a_board_with_name(CLAZZ)
			.and().a_board_with_name(CLAZZ+"1")
			.and().new_name_for_board(CLAZZ+"2")
			.and().request_to_endpoint();
		when().request_is_invoked();
		then().board_should_be_renamed()
			.and().renamed_board_should_be_returned();
	}
	
	@Test
	@WithMockUser(username = "user")
	public void should_return_bad_request_when_rename_invoked_and_board_with_the_same_name_exist() throws Exception{
		given().an_user_with_name("user").a_board_with_name(CLAZZ)
			.and().a_board_with_name(CLAZZ+"1")
			.and().new_name_for_board(CLAZZ)
			.and().request_to_endpoint();
		when().request_is_invoked();
		then().should_return_bad_request();
	}
}
