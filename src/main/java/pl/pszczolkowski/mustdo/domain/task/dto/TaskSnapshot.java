package pl.pszczolkowski.mustdo.domain.task.dto;

public class TaskSnapshot {

   private final Long id;
   private final Long tasksListId;
   private final Long boardId;
   private final String title;
   private final String description;

   public TaskSnapshot(Long id, Long tasksListId, Long boardId, String title, String description) {
      this.id = id;
      this.tasksListId = tasksListId;
      this.boardId = boardId;
      this.title = title;
      this.description = description;
   }

   public Long getId() {
      return id;
   }

   public Long getTasksListId() {
      return tasksListId;
   }
   
   public Long getBoardId() {
      return boardId;
   }

   public String getTitle() {
      return title;
   }

   public String getDescription() {
      return description;
   }

}
