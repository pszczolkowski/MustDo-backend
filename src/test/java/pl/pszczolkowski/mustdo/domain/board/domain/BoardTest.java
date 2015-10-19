package pl.pszczolkowski.mustdo.domain.board.domain;

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
import pl.pszczolkowski.mustdo.domain.board.domain.steps.GivenBoard;
import pl.pszczolkowski.mustdo.domain.board.domain.steps.ThenBoard;
import pl.pszczolkowski.mustdo.domain.board.domain.steps.WhenBoard;
import pl.pszczolkowski.mustdo.domain.board.repository.BoardRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class BoardTest extends ScenarioTest<GivenBoard, WhenBoard, ThenBoard>{
	
	private static final String CLAZZ = BoardTest.class.getSimpleName();
	
	@Autowired
	@ProvidedScenarioState
	private BoardRepository boardRepository;
	
	@After
	public void tearDown() {
		boardRepository.deleteAll();
	}
	
	@Test
	public void should_throw_EntityInStateNewException_when_toSnapshot_invoked_and_entity_is_not_persisted_yet(){
		given().a_not_persisted_board();
		when().to_snapshot_is_invoked();
		then().EntityInStateNewException_should_be_thrown();
	}
	
	@Test
	public void should_return_snapshot(){
		given().a_persisted_board();
		when().to_snapshot_is_invoked();
		then().snapshot_should_be_returned();
	}
	
	@Test
	public void should_rename_board(){
		given().a_persisted_board_with_name(CLAZZ);
		when().rename_is_invoked();
		then().board_should_be_renamed();
	}
}
