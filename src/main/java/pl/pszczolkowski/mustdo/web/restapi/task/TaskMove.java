package pl.pszczolkowski.mustdo.web.restapi.task;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class TaskMove {

   @NotNull
   private Long id;
   @NotNull
   private Long tasksListId;

   @ApiModelProperty("Task unique identifier")
   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   @ApiModelProperty("Unique identifier of Tasks List that Task should be linked to")
   public Long getTasksListId() {
      return tasksListId;
   }

   public void setTasksListId(Long tasksListId) {
      this.tasksListId = tasksListId;
   }

}
