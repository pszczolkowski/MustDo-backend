package pl.pszczolkowski.mustdo.domain.team.bo.steps;

import java.util.List;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ScenarioState.Resolution;

import pl.pszczolkowski.mustdo.domain.team.dto.TeamSnapshot;
import pl.pszczolkowski.mustdo.domain.team.repository.TeamRepository;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class ThenTeamBO
   extends Stage<ThenTeamBO> {
   
   @ExpectedScenarioState(resolution = Resolution.NAME)
   private TeamSnapshot teamSnapshot;
   @ExpectedScenarioState(resolution = Resolution.NAME)
   private TeamSnapshot snapshotOfExistingTeam;
   @ExpectedScenarioState
   private String name;
   @ExpectedScenarioState
   private Long userId;
   @ExpectedScenarioState
   private List<Long> teamMembersIds;
   @ExpectedScenarioState
   private TeamRepository teamRepository;
   
   public ThenTeamBO should_create_new_team_with_one_team_member() {
      assertNotNull(teamSnapshot);
      assertThat(teamSnapshot.getName(), is(equalTo(name)));
      assertThat(teamSnapshot.getTeamMembersIds().contains(userId), is(true));
      assertThat(teamSnapshot.getTeamMembersIds().size(), is(equalTo(1)));
      return this;
   }
   
   public ThenTeamBO should_create_new_team_with_more_than_one_team_member_id() {
      assertNotNull(teamSnapshot);
      assertThat(teamSnapshot.getName(), is(equalTo(name)));
      assertThat(teamSnapshot.getTeamMembersIds().contains(userId), is(true));
      assertThat(teamSnapshot.getTeamMembersIds().containsAll(teamMembersIds), is(true));
      assertThat(teamSnapshot.getTeamMembersIds().size(), is(equalTo(teamMembersIds.size() + 1)));
      return this;
   }
   
   public ThenTeamBO team_should_have_new_member_id() {
      TeamSnapshot existingTeamSnapshot = teamRepository.findOne(snapshotOfExistingTeam.getId()).toSnapshot();
      
      assertThat(existingTeamSnapshot.getTeamMembersIds().contains(userId), is(true));
      return this;
   }

   public ThenTeamBO team_shouldnt_contain_member_with_id() {
      TeamSnapshot existingTeamSnapshot = teamRepository.findOne(snapshotOfExistingTeam.getId()).toSnapshot();
      
      assertThat(existingTeamSnapshot.getTeamMembersIds().contains(userId), is(false));      
      return this;
   }
   
}
