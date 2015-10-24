package pl.pszczolkowski.mustdo.web.restapi.tasksList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

import pl.pszczolkowski.mustdo.domain.task.finder.TasksListSnapshotFinder;
import pl.pszczolkowski.mustdo.sharedkernel.annotations.RestValidator;
import pl.pszczolkowski.mustdo.web.restapi.commonvalidation.AbstractValidator;

@RestValidator
public class TasksListNewValidator
   extends AbstractValidator {

   private final TasksListSnapshotFinder tasksListSnapshotFinder;

   @Autowired
   public TasksListNewValidator(TasksListSnapshotFinder tasksListSnapshotFinder1) {
      this.tasksListSnapshotFinder = tasksListSnapshotFinder1;
   }

   @Override
   public boolean supports(Class<?> clazz) {
      return TasksListNew.class.isAssignableFrom(clazz);
   }

   @Override
   public void customValidation(Object target, Errors errors) {
      TasksListNew tasksListNew = (TasksListNew) target;

      if (tasksListWithSuchNameAlreadyExistsOnGivenBoard(tasksListNew.getName(), tasksListNew.getBoardId())) {
         errors.rejectValue("name", "TasksListWithSuchNameAlreadyExistsOnGivenBoard");
      }
   }

   private boolean tasksListWithSuchNameAlreadyExistsOnGivenBoard(String name, Long boardId) {
      return tasksListSnapshotFinder.findOneWithNameAndBoardId(name, boardId) != null;
   }
}
