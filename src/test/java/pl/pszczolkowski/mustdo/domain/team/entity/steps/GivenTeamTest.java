package pl.pszczolkowski.mustdo.domain.team.entity.steps;

import java.util.ArrayList;
import java.util.List;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import pl.pszczolkowski.mustdo.domain.team.enty.Team;
import pl.pszczolkowski.mustdo.domain.team.repository.TeamRepository;

public class GivenTeamTest
   extends Stage<GivenTeamTest> {

   private static final String CLAZZ = GivenTeamTest.class.getSimpleName();
   private static final long USER_ID = 1L;

   @ExpectedScenarioState
   private TeamRepository teamRepository;

   @ProvidedScenarioState
   private Team team;
   @ProvidedScenarioState
   private String name;
   @ProvidedScenarioState
   private List<Long> teamMembersIds;
   @ProvidedScenarioState
   private long teamMemberId;

   public GivenTeamTest saved_team_entity() {
      name = CLAZZ;
      teamMembersIds = new ArrayList<>();
      teamMembersIds.add(USER_ID);
      team = new Team(name, USER_ID);
      team = teamRepository.save(team);
      return this;
   }

   public GivenTeamTest not_saved_entity() {
      team = new Team(CLAZZ, USER_ID);
      return this;
   }

   public void new_team_member_id() {
      teamMemberId = 2L;
   }

   public void with_team_member() {
      teamMemberId = 2L;
      team.addTeamMember(teamMemberId);
   }

}
