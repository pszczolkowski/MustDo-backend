package pl.pszczolkowski.mustdo.web.restapi.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

import pl.pszczolkowski.mustdo.domain.task.finder.TaskSnapshotFinder;
import pl.pszczolkowski.mustdo.domain.task.finder.TasksListSnapshotFinder;
import pl.pszczolkowski.mustdo.sharedkernel.annotations.RestValidator;
import pl.pszczolkowski.mustdo.web.restapi.commonvalidation.AbstractValidator;

@RestValidator
public class TaskMoveValidator
   extends AbstractValidator {

   private final TaskSnapshotFinder taskSnapshotFinder;
   private final TasksListSnapshotFinder tasksListSnapshotFinder;

   @Autowired
   public TaskMoveValidator(TaskSnapshotFinder taskSnapshotFinder, TasksListSnapshotFinder tasksListSnapshotFinder) {
      this.taskSnapshotFinder = taskSnapshotFinder;
      this.tasksListSnapshotFinder = tasksListSnapshotFinder;
   }

   @Override
   public boolean supports(Class<?> clazz) {
      return TaskMove.class.isAssignableFrom(clazz);
   }

   @Override
   public void customValidation(Object target, Errors errors) {
      TaskMove taskMove = (TaskMove) target;

      if (taskSnapshotFinder.findOneById(taskMove.getId()) == null) {
         errors.rejectValue("id", "TaskDoesNotExist");
      }
      if (tasksListSnapshotFinder.findOneById(taskMove.getListId()) == null) {
         errors.rejectValue("tasksListId", "TasksListDoesNotExist");

      }
   }

}
