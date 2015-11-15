package pl.pszczolkowski.mustdo.web.restapi.task;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import pl.pszczolkowski.mustdo.domain.task.bo.TaskBO;
import pl.pszczolkowski.mustdo.domain.task.dto.TaskSnapshot;
import pl.pszczolkowski.mustdo.domain.task.finder.TaskSnapshotFinder;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/task")
public class TaskApi {

   private final TaskBO taskBO;
   private final TaskSnapshotFinder taskSnapshotFinder;
   private final Validator taskNewValidator;
   private final Validator taskMoveValidator;
   private final Validator taskEditValidator;

	@Autowired
	public TaskApi(TaskBO taskBO, TaskSnapshotFinder taskSnapshotFinder,
			@Qualifier("taskNewValidator") Validator taskNewValidator,
			@Qualifier("taskMoveValidator") Validator taskMoveValidator,
			@Qualifier("taskEditValidator") Validator taskEditValidator) {
		this.taskBO = taskBO;
		this.taskSnapshotFinder = taskSnapshotFinder;
		this.taskNewValidator = taskNewValidator;
		this.taskMoveValidator = taskMoveValidator;
		this.taskEditValidator = taskEditValidator;
	}

	@InitBinder("taskNew")
	protected void initNewBinder(WebDataBinder binder) {
		binder.setValidator(taskNewValidator);
	}
	
   @InitBinder("taskMove")
   protected void initMoveBinder(WebDataBinder binder) {
      binder.setValidator(taskMoveValidator);
   }

   @InitBinder("taskEdit")
   protected void initEditBinder(WebDataBinder binder) {
      binder.setValidator(taskEditValidator);
   }

   @ApiOperation(
      value = "Get Task with given id",
      notes = "Returns Task")
   @ApiResponses({
      @ApiResponse(code = 200,
         message = "Found Task with id"),
      @ApiResponse(code = 404,
         message = "Task doesn't exists")})
   @RequestMapping(value = "/{id}", method = GET)
   public ResponseEntity<Task> get(@PathVariable("id") Long id) {
      TaskSnapshot taskSnapshot = taskSnapshotFinder.findOneById(id);

      if (taskSnapshot == null) {
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
      return new ResponseEntity<>(new Task(taskSnapshot), HttpStatus.OK);
   }

   @ApiOperation(
      value = "Create new Task",
      notes = "Return added Task or BadRequest if given input was invalid")
   @ApiResponses({
      @ApiResponse(code = 200,
         message = "Task added"),
      @ApiResponse(code = 400,
         message = "Invalid input")})
   @RequestMapping(
      method = RequestMethod.POST,
      consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<Task> add(@Valid @RequestBody TaskNew taskNew) {
	   TaskSnapshot taskSnapshot = taskBO.add(taskNew.getListId(), taskNew.getTitle(), taskNew.getDescription());
	   
       return ResponseEntity
    		   .ok()
    		   .body(new Task(taskSnapshot));
   }

   @ApiOperation(
      value = "Edit existing Task",
      notes = "Return empty body")
   @ApiResponses({
      @ApiResponse(code = 200,
         message = "Task edited")})
   @RequestMapping(
      method = RequestMethod.PUT,
      consumes = APPLICATION_JSON_VALUE)
   public ResponseEntity<Task> edit(@Valid @RequestBody TaskEdit taskEdit) {
      taskBO.edit(taskEdit.getId(), taskEdit.getTitle(), taskEdit.getDescription());

      return new ResponseEntity<>(HttpStatus.OK);
   }

   @ApiOperation(
      value = "Delete existing Task",
      notes = "Returns empty body")
   @ApiResponses({
      @ApiResponse(code = 200,
         message = "Task deleted")})
   @RequestMapping(
      value = "/{id}",
      method = RequestMethod.DELETE)
   public ResponseEntity<Task> delete(@PathVariable("id") Long id) {
      if (taskSnapshotFinder.findOneById(id) != null) {
         taskBO.delete(id);
      }
      return new ResponseEntity<>(HttpStatus.OK);
   }

   @ApiOperation(
      value = "Move existing Task to antoher TaskList",
      notes = "Returns empty body")
   @ApiResponses({
      @ApiResponse(code = 200,
         message = "Task moved")})
   @RequestMapping(
      value = "/move",
      method = RequestMethod.POST,
      consumes = APPLICATION_JSON_VALUE)
   public ResponseEntity<Task> move(@Valid @RequestBody TaskMove taskMove) {
      taskBO.moveToAntoherTasksList(taskMove.getId(), taskMove.getTasksListId());

      return new ResponseEntity<>(HttpStatus.OK);

   }

}
