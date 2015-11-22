package pl.pszczolkowski.mustdo.domain.task.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import pl.pszczolkowski.mustdo.domain.task.dto.TaskSnapshot;
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
   @Size(min = 2, max = 100)
   private String name;

   @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "tasksList", orphanRemoval = true)
   @OrderBy("position")
   private List<Task> tasks = new ArrayList<>();

   protected TasksList() {
   }

   int taskCount() {
      return tasks.size();
   }

   public TasksList(String name, Long boardId) {
      this.name = name;
      this.boardId = boardId;
   }

   public void rename(String name) {
      this.name = name;
   }

   public void addTask(Task task) {
      tasks.add(task);
   }

   public TasksListSnapshot toSnapshot() {
      if (id == null) {
         throw new EntityInStateNewException();
      }
      List<TaskSnapshot> taskSnapshots = tasks
         .stream()
         .map(Task::toSnapshot)
         .collect(Collectors.toList());

      return new TasksListSnapshot(id, boardId, name, taskSnapshots);
   }

	Long getId() {
		return id;
	}

}
