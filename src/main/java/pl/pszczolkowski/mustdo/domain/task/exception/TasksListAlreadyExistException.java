package pl.pszczolkowski.mustdo.domain.task.exception;

public class TasksListAlreadyExistException
   extends RuntimeException {

   private static final long serialVersionUID = -1794537307362685773L;

   public TasksListAlreadyExistException() {
      super("Task list with such name already exists");
   }
}
