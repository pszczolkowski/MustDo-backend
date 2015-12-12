package pl.pszczolkowski.mustdo.domain.file.finder;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import pl.pszczolkowski.mustdo.domain.file.dto.FileSnapshot;
import pl.pszczolkowski.mustdo.domain.file.entity.File;
import pl.pszczolkowski.mustdo.domain.file.repo.FileRepository;
import pl.pszczolkowski.mustdo.sharedkernel.annotations.Finder;

@Finder
public class FileSnapshotFinderImpl implements FileSnapshotFinder {
	private final FileRepository fileRepository;

	@Autowired
	public FileSnapshotFinderImpl(FileRepository fileRepository) {
		this.fileRepository = fileRepository;
	}

	@Override
	public FileSnapshot findById(Long fileId) {
		File file = fileRepository.findOne(fileId);
		return file == null ? null : file.toSnapshot();
	}

	@Override
	public List<FileSnapshot> findByIds(List<Long> filesId) {
		List<File> files = fileRepository.findAll(filesId);

		return files.stream()
				.map(File::toSnapshot)
				.collect(Collectors.toList());
	}

	@Override
	public List<FileSnapshot> findByTaskId(Long taskId) {
		List<File> files = fileRepository.findAllByTaskId(taskId);
		return files.stream()
				.map(File::toSnapshot)
				.collect(Collectors.toList());
	}
}
