package pl.pszczolkowski.mustdo.web.restapi.task;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import pl.pszczolkowski.mustdo.domain.task.bo.TaskBO;
import pl.pszczolkowski.mustdo.domain.task.dto.TaskSnapshot;
import pl.pszczolkowski.mustdo.domain.task.dto.TasksListSnapshot;
import pl.pszczolkowski.mustdo.domain.task.finder.TaskSnapshotFinder;
import pl.pszczolkowski.mustdo.domain.task.finder.TasksListSnapshotFinder;
import pl.pszczolkowski.mustdo.domain.user.dto.UserSnapshot;
import pl.pszczolkowski.mustdo.domain.user.finder.UserSnapshotFinder;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/task")
public class TaskApi {

   private final TaskBO taskBO;
   private final TaskSnapshotFinder taskSnapshotFinder;
   private final UserSnapshotFinder userSnapshotFinder;
   private final TasksListSnapshotFinder tasksListSnapshotFinder;
   private final Validator taskNewValidator;
   private final Validator taskMoveValidator;
   private final Validator taskEditValidator;
   private final Validator taskAssignValidator;

	@Autowired
	public TaskApi(TaskBO taskBO, TaskSnapshotFinder taskSnapshotFinder,
			UserSnapshotFinder userSnapshotFinder,
			TasksListSnapshotFinder tasksListSnapshotFinder,
			@Qualifier("taskNewValidator") Validator taskNewValidator,
			@Qualifier("taskMoveValidator") Validator taskMoveValidator,
			@Qualifier("taskAssignValidator") Validator taskAssignValidator,
			@Qualifier("taskEditValidator") Validator taskEditValidator) {
		this.taskBO = taskBO;
		this.taskSnapshotFinder = taskSnapshotFinder;
		this.userSnapshotFinder = userSnapshotFinder;
		this.tasksListSnapshotFinder = tasksListSnapshotFinder;
		this.taskNewValidator = taskNewValidator;
		this.taskMoveValidator = taskMoveValidator;
		this.taskEditValidator = taskEditValidator;
		this.taskAssignValidator = taskAssignValidator;
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
   @InitBinder("taskEdit")
   protected void initAssignBinder(WebDataBinder binder) {
      binder.setValidator(taskAssignValidator);
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
   
   private String getLoggedUserName(){
	   String name = SecurityContextHolder.getContext().getAuthentication().getName();
	   return name;
   }
   
   @ApiOperation(
		      value = "Get history of changes in Task with given id",
		      notes = "Returns Task's history of change")
		   @ApiResponses({
		      @ApiResponse(code = 200,
		         message = "Found Task with id"),
		      @ApiResponse(code = 404,
		         message = "Task doesn't exists")})
		   @RequestMapping(value = "/{id}/history", method = GET)
	public ResponseEntity<List<Task>> history(@PathVariable("id") Long id) {
		TaskSnapshot taskSnapshot = taskSnapshotFinder.findOneById(id);

		if (taskSnapshot == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		List<TaskSnapshot> revisions = taskSnapshotFinder.findRevisions(id);
		Set<Long> userIds = revisions.stream().map(r -> r.getId()).collect(Collectors.toSet());
	    Map<Long, UserSnapshot> users = userSnapshotFinder.findAllAsMap(userIds);
	    Set<Long> listIds  = revisions.stream().map(r->r.getTasksListId()).collect(Collectors.toSet());
	    Map<Long, TasksListSnapshot> lists = tasksListSnapshotFinder.findAllAsMap(listIds);
	    
	    
	    List<Task> tasks = revisions
	    		  .stream()
	    		  .map(b -> {
	    			 UserSnapshot userSnapshot = users.get(b.getUpdatedBy()) ;
	    			 TasksListSnapshot tasksListSnapshot = lists.get(b.getTasksListId());
	    			 return new Task(taskSnapshot, userSnapshot.getLogin(), tasksListSnapshot.getName());
	    		  })
	    		  .collect(Collectors.toList());
		
		return new ResponseEntity<>(tasks, HttpStatus.OK);
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
	   String login = getLoggedUserName();
	   UserSnapshot userSnapshot = userSnapshotFinder.findByLogin(login);
	   TaskSnapshot taskSnapshot = taskBO.add(taskNew.getListId(), taskNew.getTitle(), taskNew.getDescription(), userSnapshot.getId());
	   
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
		String login = getLoggedUserName();
		UserSnapshot userSnapshot = userSnapshotFinder.findByLogin(login);
		taskBO.edit(taskEdit.getId(), taskEdit.getTitle(), taskEdit.getDescription(), userSnapshot.getId());

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
    	 String username = getLoggedUserName();
    	 UserSnapshot userSnapshot = userSnapshotFinder.findByLogin(username);
         taskBO.delete(id, userSnapshot.getId());
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
	  String login = getLoggedUserName();
	  UserSnapshot userSnapshot = userSnapshotFinder.findByLogin(login);
	  TaskSnapshot taskSnapshot = taskSnapshotFinder.findOneById(taskMove.getId());
	  
	  if(taskSnapshot == null){
		  return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	  }
	  
	  if(taskMove.getListId() == null || taskSnapshot.getTasksListId().equals(taskMove.getListId())){
		  taskBO.changePosition(taskMove.getId(), taskMove.getPosition(), userSnapshot.getId());
	  }else{
		  taskBO.moveToAntoherTasksList(taskMove.getId(), taskMove.getListId(),taskMove.getPosition(), userSnapshot.getId());
	  }
	  
      return new ResponseEntity<>(HttpStatus.OK);

   }
   
	@RequestMapping(value = "/comment",
					method = RequestMethod.POST,
					consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<Task> addComment(@Valid @RequestBody CommentNew commentNew) {
		TaskSnapshot taskSnapshot = taskSnapshotFinder.findOneById(commentNew.getTaskId());
		if(taskSnapshot == null){
			return new ResponseEntity<Task>(HttpStatus.BAD_REQUEST);
		}
		taskBO.addComment(taskSnapshot.getId(), commentNew.getText());
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "{taskId}/comment", method = RequestMethod.GET)
	public ResponseEntity<List<Comment>> list(@PathVariable("taskId") Long taskId) {
		TaskSnapshot taskSnapshot = taskSnapshotFinder.findOneById(taskId);
		if(taskSnapshot == null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Task task = new Task(taskSnapshot);
		return new ResponseEntity<>(task.getComments(), HttpStatus.OK);
   }
   
	@RequestMapping(value = "{taskId}/assign", method = RequestMethod.POST)
   public ResponseEntity<Task> assignTask(@Valid @RequestBody TaskAssign taskAssign){
      taskBO.assignTask(taskAssign.getTaskId(), taskAssign.getUserId());
      return new ResponseEntity<>(HttpStatus.OK);
   }

}
