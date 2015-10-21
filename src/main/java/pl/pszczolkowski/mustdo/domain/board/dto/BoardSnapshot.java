package pl.pszczolkowski.mustdo.domain.board.dto;

public class BoardSnapshot {
   
   private final Long id;
   private final String name;

   public BoardSnapshot(Long id, String name) {
      this.id = id;
      this.name = name;
   }

   public Long getId() {
      return id;
   }

   public String getName() {
      return name;
   }

}
