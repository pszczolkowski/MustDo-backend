package pl.pszczolkowski.mustdo.web.restapi.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

import pl.pszczolkowski.mustdo.domain.task.finder.TaskSnapshotFinder;
import pl.pszczolkowski.mustdo.sharedkernel.annotations.RestValidator;
import pl.pszczolkowski.mustdo.web.restapi.commonvalidation.AbstractValidator;

@RestValidator
public class TaskEditValidator
   extends AbstractValidator {

   private final TaskSnapshotFinder taskSnapshotFinder;

   @Autowired
   public TaskEditValidator(TaskSnapshotFinder taskSnapshotFinder) {
      this.taskSnapshotFinder = taskSnapshotFinder;
   }

   @Override
   public boolean supports(Class<?> clazz) {
      return TaskEdit.class.isAssignableFrom(clazz);
   }

   @Override
   public void customValidation(Object target, Errors errors) {
      TaskEdit taskEdit = (TaskEdit) target;

      if (taskSnapshotFinder.findOneById(taskEdit.getId()) == null) {
         errors.rejectValue("id", "TaskDoesNotExist");
      }

   }

}
