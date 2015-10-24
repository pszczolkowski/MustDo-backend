package pl.pszczolkowski.mustdo.web.restapi.tasksList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

import pl.pszczolkowski.mustdo.domain.task.dto.TasksListSnapshot;
import pl.pszczolkowski.mustdo.domain.task.finder.TasksListSnapshotFinder;
import pl.pszczolkowski.mustdo.sharedkernel.annotations.RestValidator;
import pl.pszczolkowski.mustdo.web.restapi.commonvalidation.AbstractValidator;

@RestValidator
public class TasksListRenameValidator
   extends AbstractValidator {

   private final TasksListSnapshotFinder tasksListSnapshotFinder;

   @Autowired
   public TasksListRenameValidator(TasksListSnapshotFinder tasksListSnapshotFinder) {
      this.tasksListSnapshotFinder = tasksListSnapshotFinder;
   }

   @Override
   public boolean supports(Class<?> clazz) {
      return TasksListRename.class.isAssignableFrom(clazz);
   }

   @Override
   public void customValidation(Object target, Errors errors) {
      TasksListRename tasksListRename = (TasksListRename) target;

      if (tasksListWithSuchNameAlreadyExistsOnGivenBoard(tasksListRename)) {
         errors.rejectValue("name", "TasksListWithSuchNameAlreadyExistsOnGivenBoard");
      }
   }

   private boolean tasksListWithSuchNameAlreadyExistsOnGivenBoard(TasksListRename tasksListRename) {
      TasksListSnapshot tasksListSnapshot = tasksListSnapshotFinder.findOneWithNameAndBoardId(tasksListRename.getName(),
         tasksListRename.getBoardId());
      return tasksListSnapshot != null && !tasksListSnapshot.getId().equals(tasksListRename.getId());
   }
}
