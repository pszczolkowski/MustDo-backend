package pl.pszczolkowski.mustdo.domain.task.dto;

import java.time.LocalDateTime;

public class CommentSnapshot {

	private final Long id;
	private final Long taskId;
	private final String text;
	private final LocalDateTime createdAt; 

	public CommentSnapshot(Long id, Long taskId, String text, LocalDateTime createdAt) {
		this.id = id;
		this.taskId = taskId;
		this.text = text;
		this.createdAt = createdAt;
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
