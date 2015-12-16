package pl.pszczolkowski.mustdo.domain.board.dto;

public class BoardSnapshot {

   private final Long id;
   private final String name;
   private final Long teamId;
   private final Boolean isPublic;

   public BoardSnapshot(Long id, String name, Long teamId, Boolean isPublic) {
      this.id = id;
      this.name = name;
      this.teamId = teamId;
      this.isPublic = isPublic;
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
   
   public Boolean isPublic(){
	   return isPublic;
   }

}
