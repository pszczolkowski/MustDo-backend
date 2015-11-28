package pl.pszczolkowski.mustdo.domain.team.bo;

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
import pl.pszczolkowski.mustdo.domain.team.bo.steps.GivenTeamBO;
import pl.pszczolkowski.mustdo.domain.team.bo.steps.ThenTeamBO;
import pl.pszczolkowski.mustdo.domain.team.bo.steps.WhenTeamBO;
import pl.pszczolkowski.mustdo.domain.team.repository.TeamRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class TeamBOTest
   extends ScenarioTest<GivenTeamBO, WhenTeamBO, ThenTeamBO> {

   @Autowired
   @ProvidedScenarioState
   private TeamBO teamBO;

   @Autowired
   @ProvidedScenarioState
   private TeamRepository teamRepository;

   @After
   public void tearDown() {
      teamRepository.deleteAll();
   }

   @Test
   public void should_add_team_with_one_team_member() {
      given().a_team_name().and().user_id();

      when().add_team_invoked();

      then().should_create_new_team_with_one_team_member();
   }
   @Test
   public void should_add_new_team_with_team_members() {
      given().a_team_name().and().user_id().and().a_list_with_team_members_ids();

      when().add_team_invoked_with_team_members_ids_specified();

      then().should_create_new_team_with_more_than_one_team_member_id();
   }
   
   @Test
   public void should_add_new_team_member_to_team(){
      given().a_team().and().user_id();
      when().add_team_member_invoked();
      then().team_should_have_new_member_id();
   }
   
   @Test
   public void should_remove_team_member_from_team(){
      given().a_team().and().a_team_member_id();
      when().remove_team_member_invoked();
      then().team_shouldnt_contain_member_with_id();
   }

}
