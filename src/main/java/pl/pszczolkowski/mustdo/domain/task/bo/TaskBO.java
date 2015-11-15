package pl.pszczolkowski.mustdo.domain.task.bo;

import pl.pszczolkowski.mustdo.domain.task.dto.TaskSnapshot;

public interface TaskBO {

   TaskSnapshot add(Long tasksListId, String title, String description);

   void moveToAntoherTasksList(Long id, Long tasksListId);

   void edit(Long id, String title, String description);

   void delete(Long id);

}
