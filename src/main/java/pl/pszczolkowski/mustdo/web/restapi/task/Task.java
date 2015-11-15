package pl.pszczolkowski.mustdo.web.restapi.task;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import pl.pszczolkowski.mustdo.domain.task.dto.TaskSnapshot;

@ApiModel
public class Task {

   private final Long id;
   private final String title;
   private final String description;
   private final Long listId;

   public Task(TaskSnapshot taskSnapshot) {
      this.id = taskSnapshot.getId();
      this.listId = taskSnapshot.getTasksListId();
      this.title = taskSnapshot.getTitle();
      this.description = taskSnapshot.getDescription();
   }

   @ApiModelProperty("Task unique identifier")
   public Long getId() {
      return id;
   }

   @ApiModelProperty("Task title")
   public String getTitle() {
      return title;
   }

   @ApiModelProperty("Task description")
   public String getDescription() {
      return description;
   }

   @ApiModelProperty("Unique identifier of List that Task is linked to")
   public Long getListId() {
      return listId;
   }

}
