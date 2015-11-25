package pl.pszczolkowski.mustdo.web.restapi.file;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "bytes" })
public class FileNew {
	@NotEmpty
	private String fileName;
	@NotNull
	private Long taskId;
	@NotNull
	private MultipartFile file;
	
	public MultipartFile getFile() {
		return file;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public Long getTaskId() {
		return taskId;
	}
}
