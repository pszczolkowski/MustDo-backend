package pl.pszczolkowski.mustdo.domain.task.dto;

public class TasksListSnapshot {

   private final Long id;
   private final Long boardId;
   private final String name;

   public TasksListSnapshot(Long id, Long boardId, String name) {
      this.id = id;
      this.boardId = boardId;
      this.name = name;
   }

   public Long getId() {
      return id;
   }

   public Long getBoardId() {
      return boardId;
   }

   public String getName() {
      return name;
   }

}
