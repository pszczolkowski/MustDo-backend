package pl.pszczolkowski.mustdo.web.restapi.TasksList;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class TasksListRename {

   @NotNull
   private Long id;
   @NotNull
   @Size(min = 2,
      max = 100)
   private String name;
   @NotNull
   private Long boardId;

   @ApiModelProperty(value = "Unique identifier of Task List",
      required = true)
   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   @ApiModelProperty(
      value = "Tasks List name",
      required = true)
   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   @ApiModelProperty(value = "Unique identifier of Board that Task List belongs to",
      required = true)
   public Long getBoardId() {
      return boardId;
   }

   public void setBoardId(Long boardId) {
      this.boardId = boardId;
   }
   
}
