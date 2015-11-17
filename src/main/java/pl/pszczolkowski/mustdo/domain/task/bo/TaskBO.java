package pl.pszczolkowski.mustdo.domain.task.bo;

import pl.pszczolkowski.mustdo.domain.task.dto.TaskSnapshot;

public interface TaskBO {

   TaskSnapshot add(Long tasksListId, String title, String description, Long createdBy);

   void moveToAntoherTasksList(Long id, Long tasksListId, Long updatedBy);

   void edit(Long id, String title, String description, Long updatedBy);

   void delete(Long id, Long updatedBy);

}
