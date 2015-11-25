package pl.pszczolkowski.mustdo.domain.file.bo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import pl.pszczolkowski.mustdo.domain.file.dto.FileSnapshot;
import pl.pszczolkowski.mustdo.domain.file.entity.File;
import pl.pszczolkowski.mustdo.domain.file.event.FileDeleteEvent;
import pl.pszczolkowski.mustdo.domain.file.repo.FileRepository;
import pl.pszczolkowski.mustdo.domain.task.entity.Task;
import pl.pszczolkowski.mustdo.domain.task.repository.TaskRepository;
import pl.pszczolkowski.mustdo.sharedkernel.annotations.BussinesObject;

@BussinesObject
public class FileBOImpl implements FileBO, ApplicationEventPublisherAware {

	 private static final Logger LOGGER = LoggerFactory.getLogger(FileBOImpl.class);

	   private final FileRepository fileRepository;
	   private final TaskRepository taskRepository;

	   private ApplicationEventPublisher eventPublisher;

	   @Autowired
	   public FileBOImpl(FileRepository fileRepository, TaskRepository taskRepository) {
	      this.fileRepository = fileRepository;
	      this.taskRepository = taskRepository;
	   }

	   @Override
	   public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
	      this.eventPublisher = applicationEventPublisher;
	   }

	   @Override
	   public FileSnapshot add(String fileName, String fileExtension, byte[] fileByte, Long taskId) {
	      File file = new File(fileName, fileExtension, fileByte, taskId);

	      file = fileRepository.save(file);

	      FileSnapshot fileSnapshot = file.toSnapshot();

	      LOGGER.info("Add File <{}> <{}.{}>",
	         fileSnapshot.getId(),
	         fileSnapshot.getFileName(),
	         fileSnapshot.getFileExtension());

	      return fileSnapshot;
	   }

	   @Override
	   public void delete(Long fileId) {
	      File file = fileRepository.findOne(fileId);
	      fileRepository.delete(fileId);

	      eventPublisher.publishEvent(new FileDeleteEvent(file.toSnapshot()));

	      LOGGER.info("Delete File <{}>", fileId);
	   }
}
