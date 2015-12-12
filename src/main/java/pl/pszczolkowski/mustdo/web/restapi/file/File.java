package pl.pszczolkowski.mustdo.web.restapi.file;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonView;

import pl.pszczolkowski.mustdo.domain.file.dto.FileSnapshot;

public class File{

	@JsonView(View.Summary.class)
	private Long fileId;

	@JsonView(View.Summary.class)
	private String fileName;

	@JsonView(View.Summary.class)
	private String fileExtension;

	private byte[] fileByte;

	@JsonView(View.Summary.class)
	private LocalDateTime createdAt;

	public File(FileSnapshot fileSnapshot) {
		this.fileId = fileSnapshot.getId();
		this.fileName = fileSnapshot.getFileName();
		this.fileExtension = fileSnapshot.getFileExtension();
		this.fileByte = fileSnapshot.getFileByte();

	}

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public byte[] getFileByte() {
		return fileByte.clone();
	}

	public void setFileByte(byte[] fileByte) {
		this.fileByte = fileByte.clone();
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
