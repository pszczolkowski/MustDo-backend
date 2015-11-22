package pl.pszczolkowski.mustdo.web.restapi.task;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import pl.pszczolkowski.mustdo.domain.task.bo.TasksListBO;
import pl.pszczolkowski.mustdo.domain.task.dto.TasksListSnapshot;
import pl.pszczolkowski.mustdo.domain.task.finder.TasksListSnapshotFinder;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/list")
public class TasksListApi {

   private final TasksListSnapshotFinder tasksListSnapshotFInder;
   private final TasksListBO tasksListBO;
   private final Validator tasksListNewValidator;
   private final Validator tasksListRenameValidator;

   @Autowired
   public TasksListApi(TasksListSnapshotFinder tasksListSnapshotFInder, TasksListBO tasksListBO,
      @Qualifier("tasksListNewValidator") Validator tasksListNewValidator,
      @Qualifier("tasksListRenameValidator") Validator tasksListRenameValidator) {
      this.tasksListSnapshotFInder = tasksListSnapshotFInder;
      this.tasksListBO = tasksListBO;
      this.tasksListNewValidator = tasksListNewValidator;
      this.tasksListRenameValidator = tasksListRenameValidator;
   }

   @InitBinder("tasksListNew")
   protected void initNewBinder(WebDataBinder binder) {
      binder.setValidator(tasksListNewValidator);
   }

   @InitBinder("tasksListRename")
   protected void initEditBinder(WebDataBinder binder) {
      binder.setValidator(tasksListRenameValidator);
   }

   @ApiOperation(
      value = "Get TasksList with id",
      notes = "Returns TasksList with given id, returns NotFound if not found")
   @ApiResponses({
      @ApiResponse(code = 200,
         message = "Found TasksList with given id"),
      @ApiResponse(code = 404,
         message = "TasksList doesn't exists")})
   @RequestMapping(value = "/{listId}",
      method = GET)
   public ResponseEntity<TasksList> get(@PathVariable("listId") Long listId) {
      TasksListSnapshot tasksListSnapshot = tasksListSnapshotFInder.findOneById(listId);

      return tasksListSnapshot == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
         : new ResponseEntity<>(new TasksList(tasksListSnapshot), HttpStatus.OK);
   }

   @ApiOperation(
      value = "Get list of TasksList that belongs to Board with given id",
      notes = "Returns List of TasksLists that belongs to Board with given id")
   @ApiResponses({
      @ApiResponse(code = 200,
         message = "Found Tasks Lists for board with id")})
   @RequestMapping(method = GET)
   public HttpEntity<List<TasksList>> boardList(
      @RequestParam(value = "boardId",
         required = true) Long boardId) {

      List<TasksList> tasksLists = tasksListSnapshotFInder
         .findAllByBoardId(boardId)
         .stream()
         .map(TasksList::new)
         .collect(Collectors.toList());

      return new ResponseEntity<>(tasksLists, HttpStatus.OK);

   }

   @ApiOperation(
      value = "Create new Tasks List",
      notes = "Return added Tasks List or BadRequest if given input was invalid")
   @ApiResponses({
      @ApiResponse(code = 200,
         message = "Tasks List added"),
      @ApiResponse(code = 400,
         message = "Invalid input")})
   @RequestMapping(
      method = RequestMethod.POST,
      consumes = APPLICATION_JSON_VALUE)
   public HttpEntity<TasksList> add(@Valid @RequestBody TasksListNew tasksListNew) {
      TasksListSnapshot tasksListSnapshot = tasksListBO.add(tasksListNew.getName(), tasksListNew.getBoardId());

      return new ResponseEntity<>(new TasksList(tasksListSnapshot), HttpStatus.OK);
   }

   @ApiOperation(
      value = "Rename existing Tasks List",
      notes = "Return rename Tasks List or BadRequest if given input was invalid")
   @ApiResponses({
      @ApiResponse(code = 200,
         message = "Tasks List renamed"),
      @ApiResponse(code = 400,
         message = "Invalid input")})
   @RequestMapping(
      method = RequestMethod.PUT,
      consumes = APPLICATION_JSON_VALUE)
   public HttpEntity<TasksList> rename(@Valid @RequestBody TasksListRename tasksListRename) {
      TasksListSnapshot tasksListSnapshot = tasksListBO.rename(tasksListRename.getId(), tasksListRename.getName());

      return new ResponseEntity<>(new TasksList(tasksListSnapshot), HttpStatus.OK);
   }

   @ApiOperation(
      value = "Delete existing Tasks List",
      notes = "Returns empty body")
   @ApiResponses({
      @ApiResponse(code = 200,
         message = "Board deleted")})
   @RequestMapping(
      value = "/{id}",
      method = RequestMethod.DELETE)
   public HttpEntity<TasksList> delete(@PathVariable("id") Long id) {
      if (tasksListSnapshotFInder.findOneById(id) != null) {
         tasksListBO.delete(id);
      }
      return new ResponseEntity<>(HttpStatus.OK);
   }

}
