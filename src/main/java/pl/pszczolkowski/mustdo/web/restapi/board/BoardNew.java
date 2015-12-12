package pl.pszczolkowski.mustdo.web.restapi.board;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class BoardNew {

   @NotNull
   @Size(min = 3,
      max = 100)
   private String name;

   private Long existingTeamId;

   private String teamName;

   @ApiModelProperty(
      value = "Name of board",
      required = true)
   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   @ApiModelProperty(
      value = "Unique identifier of Team which will be allowed to work on board",
      required = true)
   public Long getExistingTeamId() {
      return existingTeamId;
   }

   public void setExistingTeamId(Long existingTeamId) {
      this.existingTeamId = existingTeamId;
   }

   @ApiModelProperty(
      value = "Name of a new team that will be assigned to board",
      required = true)
   public String getTeamName() {
      return teamName;
   }

   public void setTeamName(String newTeamName) {
      this.teamName = newTeamName;
   }

}
