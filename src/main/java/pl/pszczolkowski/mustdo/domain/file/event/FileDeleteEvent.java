package pl.pszczolkowski.mustdo.domain.file.event;

import org.springframework.context.ApplicationEvent;

import pl.pszczolkowski.mustdo.domain.file.dto.FileSnapshot;

public class FileDeleteEvent extends ApplicationEvent{
	private static final long serialVersionUID = 8956487119502880478L;

	private final FileSnapshot fileSnapshot;

	public FileDeleteEvent(FileSnapshot fileSnapshot) {
		super(fileSnapshot);
		this.fileSnapshot = fileSnapshot;
	}

	public FileSnapshot getFileSnapshot() {
		return fileSnapshot;
	}
}
