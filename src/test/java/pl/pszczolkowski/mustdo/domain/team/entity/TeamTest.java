package pl.pszczolkowski.mustdo.domain.team.entity;

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
import pl.pszczolkowski.mustdo.domain.team.entity.steps.GivenTeamTest;
import pl.pszczolkowski.mustdo.domain.team.entity.steps.ThenTeamTest;
import pl.pszczolkowski.mustdo.domain.team.entity.steps.WhenTeamTest;
import pl.pszczolkowski.mustdo.domain.team.repository.TeamRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class TeamTest
   extends ScenarioTest<GivenTeamTest, WhenTeamTest, ThenTeamTest> {

   @Autowired
   @ProvidedScenarioState
   private TeamRepository teamRepository;
   
   @After
   public void tearDown(){
      teamRepository.deleteAll();
   }
   
   @Test
   public void should_create_new_Team_with_one_team_member(){
      given().saved_team_entity();
      when().toSnapshot_is_invoked();
      then().snapshot_should_be_returned();
   }
   
   @Test
   public void should_throw_exception_when_entity_is_not_saved(){
      given().not_saved_entity();
      when().toSnapshot_is_invoked();
      then().exception_should_be_thrown();
   }
   
   @Test
   public void should_add_new_team_member(){
      given().saved_team_entity().and().new_team_member_id();
      when().add_new_team_member_invoked();
      then().team_should_have_member_with_id();
   }
   @Test
   public void should_remove_team_member(){
      given().saved_team_entity().and().with_team_member();
      when().remove_team_member_invoked();
      then().team_shouldnt_have_member_with_id();
   }

}
