package pl.pszczolkowski.mustdo.domain.file.bo;

import pl.pszczolkowski.mustdo.domain.file.dto.FileSnapshot;

public interface FileBO {
	FileSnapshot add(String fileName, String fileExtension, byte[] fileByte, Long taskId);

	void delete(Long fileId);
}
