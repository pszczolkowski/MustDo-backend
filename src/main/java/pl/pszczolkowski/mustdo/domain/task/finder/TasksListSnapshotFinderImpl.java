package pl.pszczolkowski.mustdo.domain.task.finder;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import pl.pszczolkowski.mustdo.domain.task.dto.TasksListSnapshot;
import pl.pszczolkowski.mustdo.domain.task.entity.TasksList;
import pl.pszczolkowski.mustdo.domain.task.repository.TasksListRepository;
import pl.pszczolkowski.mustdo.sharedkernel.annotations.Finder;

@Finder
public class TasksListSnapshotFinderImpl
   implements TasksListSnapshotFinder {

   private final TasksListRepository tasksListRepository;

   @Autowired
   public TasksListSnapshotFinderImpl(TasksListRepository tasksListRepository) {
      this.tasksListRepository = tasksListRepository;
   }

   @Override
   public TasksListSnapshot findOneById(Long id) {
      TasksList tasksList = tasksListRepository.findOne(id);

      return tasksList == null ? null : tasksList.toSnapshot();
   }

   @Override
   public List<TasksListSnapshot> findAllByBoardId(Long boardId) {
      return tasksListRepository.findByBoardId(boardId)
         .stream()
         .map(TasksList::toSnapshot)
         .collect(Collectors.toList());
   }

   @Override
   public TasksListSnapshot findOneByNameAndBoardId(String name, Long boardId) {
      TasksList tasksList = tasksListRepository.findOneByNameAndBoardId(name, boardId);

      return tasksList == null ? null : tasksList.toSnapshot();
   }

	@Override
	public Map<Long, TasksListSnapshot> findAllAsMap(Set<Long> ids) {
		List<TasksList> tasksList = tasksListRepository.findAll(ids);
		return tasksList
				.stream()
				.map(TasksList::toSnapshot)
				.collect(Collectors.toMap(TasksListSnapshot::getId, s -> s));
	}

}
