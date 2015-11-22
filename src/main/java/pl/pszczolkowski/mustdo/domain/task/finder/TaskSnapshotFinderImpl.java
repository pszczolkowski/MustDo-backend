package pl.pszczolkowski.mustdo.domain.task.finder;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.history.Revision;

import pl.pszczolkowski.mustdo.domain.task.dto.TaskSnapshot;
import pl.pszczolkowski.mustdo.domain.task.entity.Task;
import pl.pszczolkowski.mustdo.domain.task.repository.TaskRepository;
import pl.pszczolkowski.mustdo.sharedkernel.annotations.Finder;

@Finder
public class TaskSnapshotFinderImpl
   implements TaskSnapshotFinder {

   private final TaskRepository taskRepository;

   @Autowired
   public TaskSnapshotFinderImpl(TaskRepository taskRepository) {
      this.taskRepository = taskRepository;
   }

   @Override
   public TaskSnapshot findOneById(Long id) {
      Task task = taskRepository.findOne(id);

      return task == null ? null : task.toSnapshot();
   }

   @Override
   public List<TaskSnapshot> findAllByTasksListId(Long tasksListId) {
      return taskRepository
         .findAllByTasksListId(tasksListId)
         .stream().map(Task::toSnapshot)
         .collect(Collectors.toList());
   }

   @Override
   public List<TaskSnapshot> findAllByBoardId(Long boardId) {
      return taskRepository
         .findAllByBoardId(boardId)
         .stream().map(Task::toSnapshot)
         .collect(Collectors.toList());
   }
   
   @Override
	public List<TaskSnapshot> findRevisions(Long taskId) {
		return taskRepository
				.findRevisions(taskId)
				.getContent()
				.stream()
				.sorted((a,b) -> b.getRevisionNumber() - a.getRevisionNumber())
				.map(Revision::getEntity)
				.map(Task::toSnapshot)
				.collect(Collectors.toList());
	}
}
