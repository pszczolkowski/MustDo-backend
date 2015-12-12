package pl.pszczolkowski.mustdo.web.restapi.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;

import pl.pszczolkowski.mustdo.domain.board.dto.BoardSnapshot;
import pl.pszczolkowski.mustdo.domain.board.finder.BoardSnapshotFinder;
import pl.pszczolkowski.mustdo.domain.team.dto.TeamSnapshot;
import pl.pszczolkowski.mustdo.domain.team.finder.TeamSnapshotFinder;
import pl.pszczolkowski.mustdo.domain.user.dto.UserSnapshot;
import pl.pszczolkowski.mustdo.domain.user.finder.UserSnapshotFinder;
import pl.pszczolkowski.mustdo.sharedkernel.annotations.RestValidator;
import pl.pszczolkowski.mustdo.web.restapi.commonvalidation.AbstractValidator;

@RestValidator
public class BoardRenameValidator
   extends AbstractValidator {

   private final BoardSnapshotFinder boardSnapshotFinder;
   private final UserSnapshotFinder userSnapshotFinder;
   private final TeamSnapshotFinder teamSnapshotFinder;

   @Autowired
   public BoardRenameValidator(BoardSnapshotFinder boardSnapshotFinder, UserSnapshotFinder userSnapshotFinder, TeamSnapshotFinder teamSnapshotFinder) {
      this.boardSnapshotFinder = boardSnapshotFinder;
      this.userSnapshotFinder = userSnapshotFinder;
      this.teamSnapshotFinder = teamSnapshotFinder;
   }

   @Override
   public boolean supports(Class<?> clazz) {
      return BoardRename.class.isAssignableFrom(clazz);
   }

   @Override
   public void customValidation(Object target, Errors errors) {
      BoardRename boardRename = (BoardRename) target;
      
      if(!userIsAllowedToRenameBoard(boardRename)){
         errors.rejectValue("user", "CurrentUserDoesNotHavePerrmissionToRenameBoard");
      }

      if (boardWithNameAlreadyExists(boardRename)) {
         errors.rejectValue("name", "BoardWithSuchNameAlreadyExists");
      }
   }

   private boolean boardWithNameAlreadyExists(BoardRename boardRename) {
	  BoardSnapshot boardSnapshot = boardSnapshotFinder.findOneByName(boardRename.getName());
      return boardSnapshot != null && !boardSnapshot.getId().equals(boardRename.getId());
   }
   
   private boolean userIsAllowedToRenameBoard(BoardRename boardRename) {
      UserSnapshot userSnapshot = getLoggedUserSnapshot();
      BoardSnapshot boardSnapshot = boardSnapshotFinder.findOneById(boardRename.getId());
      TeamSnapshot teamSnapshot = teamSnapshotFinder.findById(boardSnapshot.getTeamId());
      return teamSnapshot.getTeamMembersIds().contains(userSnapshot.getId());
   }
   
   private UserSnapshot getLoggedUserSnapshot() {
		String login = SecurityContextHolder.getContext().getAuthentication().getName();
		UserSnapshot userSnapshot = userSnapshotFinder.findByLogin(login);
		return userSnapshot;
	}

}
