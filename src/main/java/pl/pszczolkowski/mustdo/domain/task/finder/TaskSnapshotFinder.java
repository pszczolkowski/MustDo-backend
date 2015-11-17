package pl.pszczolkowski.mustdo.domain.task.finder;

import java.util.List;

import pl.pszczolkowski.mustdo.domain.task.dto.TaskSnapshot;

public interface TaskSnapshotFinder {
   
   TaskSnapshot findOneById(Long id);
   
   List<TaskSnapshot> findAllByTasksListId( Long tasksListId);
   
   List<TaskSnapshot> findAllByBoardId(Long boardId);
   
   List<TaskSnapshot> findRevisions(Long taskId);

}
