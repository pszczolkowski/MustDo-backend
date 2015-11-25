package pl.pszczolkowski.mustdo.domain.task.dto;

import java.time.LocalDateTime;
import java.util.List;

public class TaskSnapshot {

   private final Long id;
   private final Long tasksListId;
   private final Long boardId;
   private final String title;
   private final String description;
   private final LocalDateTime createdAt;
   private final LocalDateTime updatedAt;
   private final Long createdBy;
   private final Long updatedBy;
   private final List<CommentSnapshot> commentSnapshots;

   public TaskSnapshot(Long id, Long tasksListId, Long boardId, String title, String description, LocalDateTime createdAt, LocalDateTime updatedAt, Long createdBy, Long updatedBy, List<CommentSnapshot> commentSnapshots) {
      this.id = id;
      this.tasksListId = tasksListId;
      this.boardId = boardId;
      this.title = title;
      this.description = description;
      this.createdAt = createdAt;
      this.updatedAt = updatedAt;
      this.createdBy = createdBy;
      this.updatedBy = updatedBy;
      this.commentSnapshots = commentSnapshots;
   }

   public Long getId() {
      return id;
   }

   public Long getTasksListId() {
      return tasksListId;
   }
   
   public Long getBoardId() {
      return boardId;
   }

   public String getTitle() {
      return title;
   }

   public String getDescription() {
      return description;
   }
   
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}
	
	public List<CommentSnapshot> getCommentSnapshots() {
		return commentSnapshots;
	}
}
