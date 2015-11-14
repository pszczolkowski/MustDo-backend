package pl.pszczolkowski.mustdo.domain.task.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import pl.pszczolkowski.mustdo.domain.task.dto.TaskSnapshot;
import pl.pszczolkowski.mustdo.sharedkernel.exception.EntityInStateNewException;

@Entity
public class Task
   implements Serializable {

   private static final long serialVersionUID = -7145279649392624281L;

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;

   @NotNull
   @ManyToOne(fetch = FetchType.EAGER)
   private TasksList tasksList;

   @NotNull
   private Long boardId;

   @NotNull
   @Size(min = 2, max = 100)
   private String title;

   @Size(min = 3, max = 1000)
   private String description;
   
   @NotNull
   @Min(0)
   private int position;

   protected Task() {
   }

   public Task(TasksList tasksList, Long boardId, String title, String description) {
      this.tasksList = tasksList;
      this.boardId = boardId;
      this.title = title;
      this.description = description;
      this.position = tasksList.taskCount();
   }

   public void edit(String title, String description) {
      this.title = title;
      this.description = description;
   }

   public void moveToAnotherList(TasksList tasksListId) {
      this.tasksList = tasksListId;
   }

   public TaskSnapshot toSnapshot() {
      if (id == null) {
         throw new EntityInStateNewException();
      }
      return new TaskSnapshot(id, tasksList.toSnapshot().getId(), boardId, title, description);
   }

}
