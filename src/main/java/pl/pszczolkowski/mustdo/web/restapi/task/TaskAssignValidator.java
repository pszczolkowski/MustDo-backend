package pl.pszczolkowski.mustdo.web.restapi.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

import pl.pszczolkowski.mustdo.domain.board.finder.BoardSnapshotFinder;
import pl.pszczolkowski.mustdo.domain.task.dto.TaskSnapshot;
import pl.pszczolkowski.mustdo.domain.task.finder.TaskSnapshotFinder;
import pl.pszczolkowski.mustdo.domain.team.dto.TeamSnapshot;
import pl.pszczolkowski.mustdo.domain.team.finder.TeamSnapshotFinder;
import pl.pszczolkowski.mustdo.domain.user.dto.UserSnapshot;
import pl.pszczolkowski.mustdo.domain.user.finder.UserSnapshotFinder;
import pl.pszczolkowski.mustdo.sharedkernel.annotations.RestValidator;
import pl.pszczolkowski.mustdo.web.restapi.commonvalidation.AbstractValidator;

@RestValidator
public class TaskAssignValidator
   extends AbstractValidator {

   private final TaskSnapshotFinder taskSnapshotFinder;
   private final UserSnapshotFinder userSnapshotFinder;
   private final BoardSnapshotFinder boardSnapshotFinder;
   private final TeamSnapshotFinder teamSnapshotFinder;

   @Autowired
   public TaskAssignValidator(TaskSnapshotFinder taskSnapshotFinder, UserSnapshotFinder userSnapshotFinder,
      BoardSnapshotFinder boardSnapshotFinder, TeamSnapshotFinder teamSnapshotFinder) {
      this.taskSnapshotFinder = taskSnapshotFinder;
      this.userSnapshotFinder = userSnapshotFinder;
      this.boardSnapshotFinder = boardSnapshotFinder;
      this.teamSnapshotFinder = teamSnapshotFinder;
   }

   @Override
   public boolean supports(Class<?> clazz) {
      return TaskAssign.class.isAssignableFrom(clazz);
   }

   @Override
   public void customValidation(Object target, Errors errors) {
      TaskAssign taskAssign = (TaskAssign) target;
      TaskSnapshot taskSnapshot = taskSnapshotFinder.findOneById(taskAssign.getTaskId());
      if (taskSnapshot == null) {
         errors.rejectValue("id", "TaskDoesNotExist");
      }

      if (taskAssign.getUserId() != null) {
         UserSnapshot userSnapshot = userSnapshotFinder.findById(taskAssign.getUserId());
         if (userSnapshot == null) {
            errors.rejectValue("userId", "UserWithGivenIdDoesNotExist");
         } else {
            TeamSnapshot teamSnapshot = teamSnapshotFinder.findById(boardSnapshotFinder.findOneById(taskSnapshot.getBoardId()).getTeamId());
            if(!teamSnapshot.getTeamMembersIds().contains(userSnapshot.getId())){
               errors.rejectValue("userId", "UserWithGivenIdIsNotAMemberOfTeam");
            }
         }
      }

   }

}
