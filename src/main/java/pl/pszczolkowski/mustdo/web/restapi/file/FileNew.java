package pl.pszczolkowski.mustdo.web.restapi.file;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class FileNew {
	
	@NotNull
	private Long taskId;
	
	@ApiModelProperty("Unique identifier of Task that file should be assigned to")
	public Long getTaskId() {
		return taskId;
	}
}
