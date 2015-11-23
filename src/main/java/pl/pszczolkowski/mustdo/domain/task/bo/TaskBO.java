package pl.pszczolkowski.mustdo.domain.task.bo;

import pl.pszczolkowski.mustdo.domain.task.dto.TaskSnapshot;

public interface TaskBO {

   TaskSnapshot add(Long tasksListId, String title, String description, Long createdBy);

   void moveToAntoherTasksList(Long taskId, Long listId, int position, Long id);

   void edit(Long id, String title, String description, Long updatedBy);

   void delete(Long id, Long updatedBy);

	void changePosition(Long id, int position, Long updatedBy);
}
