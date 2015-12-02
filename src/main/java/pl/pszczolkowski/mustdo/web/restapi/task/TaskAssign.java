
package pl.pszczolkowski.mustdo.web.restapi.task;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class TaskAssign {
   @NotNull
   private Long taskId;
   
   private Long userId;

   @ApiModelProperty(value = "Unique identifier of Task List",
      required = true)
   public Long getTaskId() {
      return taskId;
   }

   public void setTaskId(Long id) {
      this.taskId = id;
   }

   @ApiModelProperty(value = "Unique identifier of User that should be assigned to task. May be null - nobody will be assigned",
      required = true)
   public Long getUserId() {
      return userId;
   }

   public void setUserId(Long userId) {
      this.userId = userId;
   }
   
   

}
