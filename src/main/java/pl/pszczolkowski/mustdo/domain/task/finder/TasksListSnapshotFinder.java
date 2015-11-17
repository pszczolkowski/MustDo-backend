package pl.pszczolkowski.mustdo.domain.task.finder;

import java.util.List;
import java.util.Map;
import java.util.Set;

import pl.pszczolkowski.mustdo.domain.task.dto.TasksListSnapshot;

public interface TasksListSnapshotFinder {

   TasksListSnapshot findOneById(Long id);

   List<TasksListSnapshot> findAllByBoardId(Long boardId);

   TasksListSnapshot findOneByNameAndBoardId(String name, Long boardId);
   
   Map<Long, TasksListSnapshot> findAllAsMap(Set<Long> ids);
}
