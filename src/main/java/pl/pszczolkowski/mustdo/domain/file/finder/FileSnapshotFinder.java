package pl.pszczolkowski.mustdo.domain.file.finder;

import java.util.List;

import pl.pszczolkowski.mustdo.domain.file.dto.FileSnapshot;

public interface FileSnapshotFinder {
	   FileSnapshot findById(Long fileId);

	   List<FileSnapshot> findByIds(List<Long> ids);
	   
	   List<FileSnapshot> findByTaskId(Long taskId);
}
