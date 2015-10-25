package pl.pszczolkowski.mustdo.domain.board.bo;

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
import pl.pszczolkowski.mustdo.domain.board.bo.steps.GivenBoardBO;
import pl.pszczolkowski.mustdo.domain.board.bo.steps.ThenBoardBO;
import pl.pszczolkowski.mustdo.domain.board.bo.steps.WhenBoardBO;
import pl.pszczolkowski.mustdo.domain.board.repository.BoardRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class BoardBOTest extends ScenarioTest<GivenBoardBO, WhenBoardBO, ThenBoardBO>{
	
	private static final String CLAZZ = BoardBO.class.getSimpleName();
	
	@Autowired
	@ProvidedScenarioState
	private BoardBO boardBO;
	@Autowired
	@ProvidedScenarioState
	private BoardRepository boardRepository;
	
	@After
	public void tearDown() {
		boardRepository.deleteAll();
	}
	
	@Test
	public void should_add_new_board(){
		given().a_name_for_board(CLAZZ);
		when().addBoard_is_invoked();
		then().board_should_be_added();
	}
	
	@Test
	public void should_throw_boardAlreadyExistException_when_add_invoked_and_board_already_exist(){
		given().a_name_for_board(CLAZZ)
			.and().a_board_with_name(CLAZZ);
		when().addBoard_is_invoked();
		then().boardAlreadyExistException_should_be_thrown();
	}
	
	@Test
	public void should_rename_existing_board(){
		given().a_board_with_name(CLAZZ);
		when().rename_is_invoked();
		then().board_should_be_renamed();
	}
	
	@Test
	public void should_throw_boardAlreadyExistException_when_rename_invoked_and_board_already_exist(){
		given().a_board_with_name(CLAZZ)
			.and().other_board_with_name(CLAZZ + "other");
		when().rename_is_invoked_with_name(CLAZZ + "other");
		then().boardAlreadyExistException_should_be_thrown();
	}
	
	@Test
	public void should_return_unchanged_board_when_rename_invoked_and_name_did_not_changed(){
		given().a_board_with_name(CLAZZ);
		when().rename_is_invoked_with_name(CLAZZ);
		then().nothing_should_have_changed();
	}
	
	@Test
	public void should_delete_board(){
		given().a_board_with_name(CLAZZ);
		when().delete_is_invoked();
		then().board_should_be_removed();
	}
}
