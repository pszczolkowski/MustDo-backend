package pl.pszczolkowski.mustdo.domain.file.dto;

import java.time.LocalDateTime;

public class FileSnapshot {

	private final Long fileId;
	private final Long taskId;
	private final String fileName;
	private final String fileExtension;
	private final byte[] fileByte;
	private final LocalDateTime fileAddTime;

	public FileSnapshot(Long fileId, Long taskId, String fileName, String fileExtension, byte[] fileByte,
			LocalDateTime fileAddtime) {
		this.fileId = fileId;
		this.taskId = taskId;
		this.fileName = fileName;
		this.fileExtension = fileExtension;
		this.fileByte = fileByte.clone();
		this.fileAddTime = fileAddtime;
	}

	public LocalDateTime getFileAddTime() {
		return fileAddTime;
	}

	public Long getId() {
		return fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public byte[] getFileByte() {
		return fileByte.clone();
	}
	
	public Long getTaskId() {
		return taskId;
	}
}