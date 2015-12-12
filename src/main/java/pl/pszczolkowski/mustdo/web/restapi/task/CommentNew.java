package pl.pszczolkowski.mustdo.web.restapi.task;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class CommentNew {
	
	@NotNull
	private Long taskId;
	@NotEmpty
	private String text;
	
	public Long getTaskId() {
		return taskId;
	}

	public String getText() {
		return text;
	}
}
