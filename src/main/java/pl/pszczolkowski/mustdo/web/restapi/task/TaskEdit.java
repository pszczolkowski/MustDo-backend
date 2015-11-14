package pl.pszczolkowski.mustdo.web.restapi.task;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class TaskEdit {

   @NotNull
   private Long id;
   @NotNull
   @Size(min = 2, max = 100)
   private String title;
   @NotNull
   @Size(min = 3, max = 1000)
   private String description;

   @ApiModelProperty("Task unique identifier")
   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
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
