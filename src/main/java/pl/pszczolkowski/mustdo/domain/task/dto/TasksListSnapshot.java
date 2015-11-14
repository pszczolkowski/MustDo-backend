package pl.pszczolkowski.mustdo.domain.task.dto;

import java.util.List;

public class TasksListSnapshot {

   private final Long id;
   private final Long boardId;
   private final String name;
   private final List<TaskSnapshot> taskSnapshots;

   public TasksListSnapshot(Long id, Long boardId, String name, List<TaskSnapshot> taskSnapshots) {
      this.id = id;
      this.boardId = boardId;
      this.name = name;
      this.taskSnapshots = taskSnapshots;
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

   public List<TaskSnapshot> getTaskSnapshots() {
      return taskSnapshots;
   }

}
