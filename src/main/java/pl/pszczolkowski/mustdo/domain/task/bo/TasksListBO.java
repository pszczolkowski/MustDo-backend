package pl.pszczolkowski.mustdo.domain.task.bo;

import pl.pszczolkowski.mustdo.domain.task.dto.TasksListSnapshot;

public interface TasksListBO {

   TasksListSnapshot add(String name, Long boardId);

   TasksListSnapshot rename(Long id, String name);
   
   void delete(Long id);

   public int removeTasksListsWithBoardId(Long boardId);

}
