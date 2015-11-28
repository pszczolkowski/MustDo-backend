package pl.pszczolkowski.mustdo.domain.team.dto;

import java.util.Set;

public class TeamSnapshot {

   private final Long id;
   private final String name;
   private final Set<Long> teamMembersIds;

   public TeamSnapshot(Long id, String name, Set<Long> teamMembersIds) {
      this.id = id;
      this.name = name;
      this.teamMembersIds = teamMembersIds;
   }

   public Long getId() {
      return id;
   }

   public String getName() {
      return name;
   }

   public Set<Long> getTeamMembersIds() {
      return teamMembersIds;
   }

}
