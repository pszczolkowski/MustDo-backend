package pl.pszczolkowski.mustdo.domain.board.dto;

public class BoardSnapshot {

   private final Long id;
   private final String name;
   private final Long teamId;

   public BoardSnapshot(Long id, String name, Long teamId) {
      this.id = id;
      this.name = name;
      this.teamId = teamId;
   }

   public Long getId() {
      return id;
   }

   public String getName() {
      return name;
   }

   public Long getTeamId() {
      return teamId;
   }

}
