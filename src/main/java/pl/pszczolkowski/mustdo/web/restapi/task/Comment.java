package pl.pszczolkowski.mustdo.web.restapi.task;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import pl.pszczolkowski.mustdo.domain.task.dto.CommentSnapshot;

@ApiModel
public class Comment {
	
	private final Long id;
	private final Long taskId;
	private final String text;
	private final LocalDateTime createdAt;
	
	public Comment(CommentSnapshot commentSnapshot) {
		this.id = commentSnapshot.getId();
		this.taskId = commentSnapshot.getTaskId();
		this.text = commentSnapshot.getText();
		this.createdAt = commentSnapshot.getCreatedAt();
	}
	
	public Long getId() {
		return id;
	}

	public Long getTaskId() {
		return taskId;
	}

	public String getText() {
		return text;
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
}
