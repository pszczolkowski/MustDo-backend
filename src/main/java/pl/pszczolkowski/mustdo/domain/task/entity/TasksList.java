package pl.pszczolkowski.mustdo.domain.task.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import pl.pszczolkowski.mustdo.domain.task.dto.TasksListSnapshot;
import pl.pszczolkowski.mustdo.sharedkernel.exception.EntityInStateNewException;

@Entity
public class TasksList
   implements Serializable {

   private static final long serialVersionUID = -5679195429580979150L;

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;

   @NotNull
   private Long boardId;

   @NotNull
   @Size(min = 2,
      max = 100)
   @Column(unique = true)
   private String name;

   protected TasksList() {
   }

   public TasksList(String name, Long boardId) {
      this.name = name;
      this.boardId = boardId;
   }

   public void rename(String name) {
      this.name = name;
   }

   public TasksListSnapshot toSnapshot() {
      if (id == null) {
         throw new EntityInStateNewException();
      }
      return new TasksListSnapshot(id, boardId, name);
   }

}
