package pl.pszczolkowski.mustdo.domain.task.finder;

import java.util.List;

import pl.pszczolkowski.mustdo.domain.task.dto.TasksListSnapshot;

public interface TasksListSnapshotFinder {

   TasksListSnapshot findOneById(Long id);

   List<TasksListSnapshot> findAllWithBoardId(Long boardId);

   TasksListSnapshot findOneWithNameAndBoardId(String name, Long boardId);
}
