package pl.pszczolkowski.mustdo.web.restapi.task;

import static java.util.stream.Collectors.toList;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import pl.pszczolkowski.mustdo.domain.task.dto.TasksListSnapshot;

@ApiModel
public class TasksList {

   private final Long id;
   private final Long boardId;
   private final String name;
   private final List<Task> tasks;

   TasksList(TasksListSnapshot tasksListSnapshot) {
      this.id = tasksListSnapshot.getId();
      this.boardId = tasksListSnapshot.getBoardId();
      this.name = tasksListSnapshot.getName();
      this.tasks = tasksListSnapshot
    		  .getTaskSnapshots()
    		  .stream()
    		  .map(Task::new)
    		  .collect(toList());
   }

   @ApiModelProperty("Unique identifier of Tasks List")
   public Long getId() {
      return id;
   }

   @ApiModelProperty("Unique identifier of Board to which Tasks List belong")
   public Long getBoardId() {
      return boardId;
   }

   @ApiModelProperty("Tasks List name")
   public String getName() {
      return name;
   }
   @ApiModelProperty("List of linked tasks")
   public List<Task> getTasks() {
      return tasks;
   }

}
