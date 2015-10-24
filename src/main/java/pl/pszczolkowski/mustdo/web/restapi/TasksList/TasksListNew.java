package pl.pszczolkowski.mustdo.web.restapi.TasksList;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class TasksListNew {

   @NotNull
   @Size(min = 3,
      max = 100)
   private String name;
   @NotNull
   private Long boardId;

   @ApiModelProperty(
      value = "Tasks List name",
      required = true)
   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   @ApiModelProperty(
      value = "Unique identifier of Board to whcich Tasks List belongs",
      required = true)
   public Long getBoardId() {
      return boardId;
   }

   public void setBoardId(Long boardId) {
      this.boardId = boardId;
   }

}
