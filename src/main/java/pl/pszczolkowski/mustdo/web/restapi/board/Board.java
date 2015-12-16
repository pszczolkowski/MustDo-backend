package pl.pszczolkowski.mustdo.web.restapi.board;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import pl.pszczolkowski.mustdo.domain.board.dto.BoardSnapshot;

@ApiModel
public class Board {

   private final Long id;
   private final String name;
   private final Boolean isPublic;

   public Board(BoardSnapshot boardSnapshot) {
      this.id = boardSnapshot.getId();
      this.name = boardSnapshot.getName();
      this.isPublic = boardSnapshot.isPublic();
   }

   @ApiModelProperty("Unique identifier of board")
   public Long getId() {
      return id;
   }

   @ApiModelProperty("Name of board")
   public String getName() {
      return name;
   }
   
   @ApiModelProperty("Visibility of board")
   public Boolean isPublic() {
      return isPublic;
   }

}
