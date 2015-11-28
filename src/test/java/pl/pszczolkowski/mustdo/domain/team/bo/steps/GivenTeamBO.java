package pl.pszczolkowski.mustdo.domain.team.bo.steps;

import java.util.ArrayList;
import java.util.List;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.annotation.ScenarioState.Resolution;

import pl.pszczolkowski.mustdo.domain.team.dto.TeamSnapshot;
import pl.pszczolkowski.mustdo.domain.team.enty.Team;
import pl.pszczolkowski.mustdo.domain.team.repository.TeamRepository;

public class GivenTeamBO
   extends Stage<GivenTeamBO> {

   private static final String TEAM_NAME = "TeamName";
   private static final long USER_ID = 3L;

   @ExpectedScenarioState
   private TeamRepository teamRepository;

   @ProvidedScenarioState
   private String name;
   @ProvidedScenarioState
   private long userId;
   @ProvidedScenarioState
   private List<Long> teamMembersIds;
   @ProvidedScenarioState(resolution = Resolution.NAME)
   private TeamSnapshot snapshotOfExistingTeam;

   public GivenTeamBO a_team_name() {
      this.name = TEAM_NAME;
      return this;
   }

   public GivenTeamBO user_id() {
      this.userId = USER_ID;
      return this;
   }

   public GivenTeamBO a_list_with_team_members_ids() {
      teamMembersIds = new ArrayList<>();
      teamMembersIds.add(4l);
      teamMembersIds.add(5l);
      teamMembersIds.add(6l);
      return this;
   }

   public GivenTeamBO a_team() {
      team = new Team(TEAM_NAME, USER_ID);
      team = teamRepository.save(team);

      this.snapshotOfExistingTeam = team.toSnapshot();
      return this;
   }
   private Team team;

   public GivenTeamBO a_team_member_id() {
      this.userId = 15L;
      team.addTeamMember(userId);
      team = teamRepository.save(team);
      return this;
   }

}
