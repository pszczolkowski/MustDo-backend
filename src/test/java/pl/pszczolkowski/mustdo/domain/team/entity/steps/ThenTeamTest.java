package pl.pszczolkowski.mustdo.domain.team.entity.steps;

import java.util.List;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;

import pl.pszczolkowski.mustdo.domain.team.dto.TeamSnapshot;
import pl.pszczolkowski.mustdo.domain.team.enty.Team;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class ThenTeamTest
   extends Stage<ThenTeamTest> {

   @ExpectedScenarioState
   private TeamSnapshot teamSnapshot;
   @ExpectedScenarioState
   private String name;
   @ExpectedScenarioState
   private Team team;
   @ExpectedScenarioState
   private List<Long> teamMembersIds;
   @ExpectedScenarioState
   private long teamMemberId;
   @ExpectedScenarioState
   private boolean entityInStateNewExceptionWasThrown;

   public ThenTeamTest snapshot_should_be_returned() {
      assertNotNull(teamSnapshot);
      assertThat(teamSnapshot.getName(), is(equalTo(name)));
      assertThat(teamSnapshot.getTeamMembersIds().containsAll(teamMembersIds), is(true));
      assertThat(teamSnapshot.getTeamMembersIds().size(), is(equalTo(teamMembersIds.size())));
      return this;
   }

   public void exception_should_be_thrown() {
      assertNull(teamSnapshot);
      assertThat(entityInStateNewExceptionWasThrown, is(true));
   }

   public void team_should_have_member_with_id() {
      TeamSnapshot teamSnapshot = team.toSnapshot();
      assertThat(teamSnapshot.getTeamMembersIds().contains(teamMemberId), is(true));
   }
   public void team_shouldnt_have_member_with_id() {
      TeamSnapshot teamSnapshot = team.toSnapshot();
      assertThat(teamSnapshot.getTeamMembersIds().contains(teamMemberId), is(false));
   }

}
