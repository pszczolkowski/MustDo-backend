package pl.pszczolkowski.mustdo.web.restapi.task;

import java.util.List;
import java.util.stream.Collectors;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import pl.pszczolkowski.mustdo.domain.task.dto.TaskSnapshot;

@ApiModel
public class Task {

   private final Long id;
   private final String title;
   private final String description;
   private final Long listId;
   private String username;
   private String listName;
   private List<Comment> comments;

   public Task(TaskSnapshot taskSnapshot) {
      this.id = taskSnapshot.getId();
      this.listId = taskSnapshot.getTasksListId();
      this.title = taskSnapshot.getTitle();
      this.description = taskSnapshot.getDescription();
      this.comments = taskSnapshot
    		  .getCommentSnapshots()
    		  .stream().map(Comment::new)
    		  .collect(Collectors.toList());
   }
   
   public Task(TaskSnapshot taskSnapshot, String username, String listName){
	   this(taskSnapshot);
	   this.username = username;
	   this.listName = listName;
   }

   @ApiModelProperty("Task unique identifier")
   public Long getId() {
      return id;
   }

   @ApiModelProperty("Task title")
   public String getTitle() {
      return title;
   }

   @ApiModelProperty("Task description")
   public String getDescription() {
      return description;
   }

   @ApiModelProperty("Unique identifier of List that Task is linked to")
   public Long getListId() {
      return listId;
   }
   
   @ApiModelProperty("Name of List that Task is linked to")
	public String getListName() {
		return listName;
	}
   
   @ApiModelProperty("Author of change in Task")
	public String getUsername() {
		return username;
	}
   
   @ApiModelProperty("Comments assigned to Task")
   public List<Comment> getComments() {
	return comments;
}

}
