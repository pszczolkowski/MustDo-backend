package pl.pszczolkowski.mustdo.domain.team.entity.steps;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import pl.pszczolkowski.mustdo.domain.team.dto.TeamSnapshot;
import pl.pszczolkowski.mustdo.domain.team.enty.Team;
import pl.pszczolkowski.mustdo.sharedkernel.exception.EntityInStateNewException;

public class WhenTeamTest
   extends Stage<WhenTeamTest> {

   @ExpectedScenarioState
   private Team team;
   @ExpectedScenarioState
   private long teamMemberId;

   @ProvidedScenarioState
   private TeamSnapshot teamSnapshot;
   @ProvidedScenarioState
   private boolean entityInStateNewExceptionWasThrown;

   public WhenTeamTest toSnapshot_is_invoked() {
      try {
         teamSnapshot = team.toSnapshot();
      } catch (EntityInStateNewException exception) {
         entityInStateNewExceptionWasThrown = true;
      }
      return this;
   }

   public WhenTeamTest add_new_team_member_invoked() {
      team.addTeamMember(teamMemberId);
      return this;
   }

   public WhenTeamTest remove_team_member_invoked() {
      team.removeTeamMember(teamMemberId);      
      return this;
   }

}
