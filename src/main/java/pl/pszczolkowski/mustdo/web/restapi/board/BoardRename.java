package pl.pszczolkowski.mustdo.web.restapi.board;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
class BoardRename {

   @NotNull
   private Long id;
   @NotNull
   @Size(min = 3,
      max = 100)
   private String name;

   @ApiModelProperty(value = "Board unique idetifier",
      required = true)
   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   @ApiModelProperty(value = "Board name",
      required = true)
   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

}
