package pl.pszczolkowski.mustdo.web.restapi.tasksList;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import pl.pszczolkowski.mustdo.domain.task.dto.TasksListSnapshot;

@ApiModel
public class TasksList {

   private final Long id;
   private final Long boardId;
   private final String name;

   TasksList(TasksListSnapshot tasksListSnapshot) {
      this.id = tasksListSnapshot.getId();
      this.boardId = tasksListSnapshot.getBoardId();
      this.name = tasksListSnapshot.getName();
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

}
