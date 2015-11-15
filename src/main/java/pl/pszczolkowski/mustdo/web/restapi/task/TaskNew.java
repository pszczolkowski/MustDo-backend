package pl.pszczolkowski.mustdo.web.restapi.task;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class TaskNew {

   @NotNull
   private Long listId;
   
   @NotNull
   @Size(min = 2, max = 100)
   private String title;
   
   @Size(min = 3, max = 1000)
   private String description;

   @ApiModelProperty("Unique identifier of List that Task is linked to")
   public Long getListId() {
      return listId;
   }

   public void setListId(Long tasksListId) {
      this.listId = tasksListId;
   }

   @ApiModelProperty("Task title")
   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   @ApiModelProperty("Task description")
   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

}
