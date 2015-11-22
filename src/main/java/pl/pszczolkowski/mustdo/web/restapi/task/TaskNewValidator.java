package pl.pszczolkowski.mustdo.web.restapi.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

import pl.pszczolkowski.mustdo.domain.task.finder.TasksListSnapshotFinder;
import pl.pszczolkowski.mustdo.sharedkernel.annotations.RestValidator;
import pl.pszczolkowski.mustdo.web.restapi.commonvalidation.AbstractValidator;

@RestValidator
public class TaskNewValidator
   extends AbstractValidator {

   private final TasksListSnapshotFinder tasksListSnapshotFinder;

   @Autowired
   public TaskNewValidator(TasksListSnapshotFinder tasksListSnapshotFinder) {
      this.tasksListSnapshotFinder = tasksListSnapshotFinder;
   }

   @Override
   public boolean supports(Class<?> clazz) {
      return TaskNew.class.isAssignableFrom(clazz);
   }

   @Override
   public void customValidation(Object target, Errors errors) {
	   TaskNew taskNew = (TaskNew) target;

      if (tasksListSnapshotFinder.findOneById(taskNew.getListId()) == null) {
         errors.rejectValue("listId", "TasksListDoesNotExist");
      }

   }

}
