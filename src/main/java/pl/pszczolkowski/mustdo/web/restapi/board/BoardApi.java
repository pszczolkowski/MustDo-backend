package pl.pszczolkowski.mustdo.web.restapi.board;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
import pl.pszczolkowski.mustdo.domain.team.bo.TeamBO;
import pl.pszczolkowski.mustdo.domain.team.dto.TeamSnapshot;
import pl.pszczolkowski.mustdo.domain.team.finder.TeamSnapshotFinder;
import pl.pszczolkowski.mustdo.domain.user.dto.UserSnapshot;
import pl.pszczolkowski.mustdo.domain.user.finder.UserSnapshotFinder;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/board")
public class BoardApi {

   private final BoardBO boardBO;
   private final TeamBO teamBO;
   private final BoardSnapshotFinder boardSnapshotFinder;
   private final UserSnapshotFinder userSnapshotFinder;
   private final TeamSnapshotFinder teamSnapshotFinder;
   private final Validator boardNewValidator;
   private final Validator boardRenameValidator;

   @Autowired
   public BoardApi(BoardBO boardBO, TeamBO teamBO, BoardSnapshotFinder boardSnapshotFinder, 
      UserSnapshotFinder userSnapshotFinder, TeamSnapshotFinder teamSnapshotFinder,
      @Qualifier("boardNewValidator") Validator boardNewValidator,
      @Qualifier("boardRenameValidator") Validator boardRenameValidator) {
      this.boardBO = boardBO;
      this.teamBO = teamBO;
      this.boardSnapshotFinder = boardSnapshotFinder;
      this.userSnapshotFinder = userSnapshotFinder;
      this.teamSnapshotFinder = teamSnapshotFinder;
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
   private UserSnapshot getLoggedUserSnapshot() {
		String login = SecurityContextHolder.getContext().getAuthentication().getName();
		UserSnapshot userSnapshot = userSnapshotFinder.findByLogin(login);
		return userSnapshot;
	}

   @ApiOperation(
		value = "Get all boards",
		notes = "Returns all available boards")
   @ApiResponses({
	    @ApiResponse(code = 200,  message = "Found list with all boards")})
   @RequestMapping(method = GET)
   public HttpEntity<List<Board>> list() {
      UserSnapshot userSnapshot = getLoggedUserSnapshot();
      Set<Long> userTeamIds = teamSnapshotFinder.findAllByUserId(userSnapshot.getId())
         .stream()
         .map(TeamSnapshot::getId)
         .collect(Collectors.toSet());
      List<Board> boards = boardSnapshotFinder
    		  .findAllByTeamIdIn(userTeamIds)
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
		@ApiResponse(code = 403, message = "Board not available for current user"),
		@ApiResponse(code = 404, message = "Board doesn't exists")})
   @RequestMapping(value = "/{boardId}",
      method = GET)
   public HttpEntity<Board> get(@PathVariable("boardId") Long boardId) {
      BoardSnapshot boardSnapshot = boardSnapshotFinder.findOneById(boardId);
      if (boardSnapshot == null) {
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
      UserSnapshot userSnapshot = getLoggedUserSnapshot();
      TeamSnapshot teamSnapshot = teamSnapshotFinder.findById(boardSnapshot.getTeamId());
      if(!teamSnapshot.getTeamMembersIds().contains(userSnapshot.getId())){
         return new ResponseEntity<>(HttpStatus.FORBIDDEN);
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
      BoardSnapshot boardSnapshot;
      if (boardNew.getExistingTeamId() == null) {
         UserSnapshot userSnapshot = getLoggedUserSnapshot();
         TeamSnapshot teamSnapshot = teamBO.add(boardNew.getNewTeamName(), userSnapshot.getId());
         boardSnapshot = boardBO.add(boardNew.getName(), teamSnapshot.getId());
      } else {
         boardSnapshot = boardBO.add(boardNew.getName(), boardNew.getExistingTeamId());
      }
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
