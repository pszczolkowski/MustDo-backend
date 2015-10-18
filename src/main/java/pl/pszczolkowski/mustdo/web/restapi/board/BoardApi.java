package pl.pszczolkowski.mustdo.web.restapi.board;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import pl.pszczolkowski.mustdo.domain.board.bo.BoardBO;
import pl.pszczolkowski.mustdo.domain.board.dto.BoardSnapshot;
import pl.pszczolkowski.mustdo.domain.board.finder.BoardSnapshotFinder;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/board")
public class BoardApi {

   private final BoardBO boardBO;
   private final BoardSnapshotFinder boardSnapshotFinder;
   private final Validator boardNewValidator;
   private final Validator boardRenameValidator;

   @Autowired
   public BoardApi(BoardBO boardBO, BoardSnapshotFinder boardSnapshotFinder,
      @Qualifier("boardNewValidator") Validator boardNewValidator,
      @Qualifier("boardRenameValidator") Validator boardRenameValidator) {
      this.boardBO = boardBO;
      this.boardSnapshotFinder = boardSnapshotFinder;
      this.boardNewValidator = boardNewValidator;
      this.boardRenameValidator = boardRenameValidator;
   }

   @InitBinder("boardNew")
   protected void initNewBinder(WebDataBinder binder) {
      binder.setValidator(boardNewValidator);
   }

   @InitBinder("boardRename")
   protected void initEditBinder(WebDataBinder binder) {
      binder.setValidator(boardRenameValidator);
   }

   @ApiOperation(
		value = "Get all boards",
		notes = "Returns all available boards")
   @ApiResponses({
	    @ApiResponse(code = 200,  message = "Found list with all boards")})
   @RequestMapping(method = GET)
   public HttpEntity<List<Board>> list() {
      List<Board> boards = boardSnapshotFinder
    		  .findAll()
    		  .stream()
    		  .map(Board::new)
    		  .collect(Collectors.toList());

      return new ResponseEntity<>(boards, HttpStatus.OK);
   }

   @ApiOperation(
		value = "Get board with id",
		notes = "Returns board with given id, return BadRequest if not found")
   @ApiResponses({
		@ApiResponse(code = 200, message = "Found board with given id"),
		@ApiResponse(code = 404, message = "Board doesn't exists")})
   @RequestMapping(value = "/{boardId}",
      method = GET)
   public HttpEntity<Board> get(@PathVariable("boardId") Long boardId) {
      BoardSnapshot boardSnapshot = boardSnapshotFinder.findOneById(boardId);
      if (boardSnapshot == null) {
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }

      return new ResponseEntity<>(new Board(boardSnapshot), HttpStatus.OK);
   }

   @ApiOperation(
		value = "Create new Board",
        notes = "Return added Board or BadRequest if given input was invalid")
   @ApiResponses({
        @ApiResponse(code = 200, message = "Board added"),
        @ApiResponse(code = 400, message = "Invalid input")})
   @RequestMapping(
		method = RequestMethod.POST,
        consumes = APPLICATION_JSON_VALUE)
   public HttpEntity<Board> add(@Valid @RequestBody BoardNew boardNew) {
      BoardSnapshot boardSnapshot = boardBO.add(boardNew.getName());

      return new ResponseEntity<>(new Board(boardSnapshot), HttpStatus.OK);
   }

   @ApiOperation(
		value = "Edit existing Board",
        notes = "Return edited Board or BadRequest if given input was invalid")
   @ApiResponses({
        @ApiResponse(code = 200, message = "Board Edited"),
        @ApiResponse(code = 400, message = "Invalid input")})
   @RequestMapping(
		method = RequestMethod.PUT,
        consumes = APPLICATION_JSON_VALUE)
   public HttpEntity<Board> rename(@Valid @RequestBody BoardRename boardRename) {
      BoardSnapshot boardSnapshot = boardBO.rename(boardRename.getId(), boardRename.getName());
      return new ResponseEntity<>(new Board(boardSnapshot), HttpStatus.OK);
   }

   @ApiOperation(
		value = "Delete existing Board",
        notes = "Returns empty body")
   @ApiResponses({
        @ApiResponse(code = 200,  message = "Board deleted")})
   @RequestMapping(
		value = "/{boardId}",
        method = RequestMethod.DELETE)
   public HttpEntity<Board> delete(@PathVariable("boardId") Long boardId) {
      boardBO.delete(boardId);
      return new ResponseEntity<>(HttpStatus.OK);
   }

}
