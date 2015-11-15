package pl.pszczolkowski.mustdo.web.restapi.task;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class TaskMove {

   @NotNull
   private Long id;
   @NotNull
   private Long listId;

   @ApiModelProperty("Unique identifier of Task")
   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   @ApiModelProperty("Unique identifier of List that Task would be linked to")
   public Long getListId() {
      return listId;
   }

   public void setListId(Long tasksListId) {
      this.listId = tasksListId;
   }

}
