package pl.pszczolkowski.mustdo.domain.team.bo.steps;

import java.util.List;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.annotation.ScenarioState.Resolution;

import pl.pszczolkowski.mustdo.domain.team.bo.TeamBO;
import pl.pszczolkowski.mustdo.domain.team.dto.TeamSnapshot;

public class WhenTeamBO
   extends Stage<WhenTeamBO> {

   @ExpectedScenarioState
   private TeamBO teamBO;
   @ExpectedScenarioState
   private String name;
   @ExpectedScenarioState
   private long userId;
   @ExpectedScenarioState(resolution = Resolution.NAME)
   private TeamSnapshot snapshotOfExistingTeam;
   @ExpectedScenarioState
   private List<Long> teamMembersIds;

   @ProvidedScenarioState(resolution = Resolution.NAME)
   private TeamSnapshot teamSnapshot;

   public WhenTeamBO add_team_invoked() {
      teamSnapshot = teamBO.add(name, userId);
      return this;
   }

   public WhenTeamBO add_team_invoked_with_team_members_ids_specified() {
      teamSnapshot = teamBO.add(name, userId, teamMembersIds);
      return this;
   }

   public WhenTeamBO add_team_member_invoked() {
      teamBO.addTeamMember(snapshotOfExistingTeam.getId(), userId);
      return this;
   }

   public WhenTeamBO remove_team_member_invoked() {
      teamBO.removeTeamMember(snapshotOfExistingTeam.getId(), userId);
      return this;
   }

}
