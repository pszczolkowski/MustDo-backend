package pl.pszczolkowski.mustdo.web.restapi.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

import pl.pszczolkowski.mustdo.domain.board.dto.BoardSnapshot;
import pl.pszczolkowski.mustdo.domain.board.finder.BoardSnapshotFinder;
import pl.pszczolkowski.mustdo.sharedkernel.annotations.RestValidator;
import pl.pszczolkowski.mustdo.web.restapi.commonvalidation.AbstractValidator;

@RestValidator
public class BoardRenameValidator
   extends AbstractValidator {

   private final BoardSnapshotFinder boardSnapshotFinder;

   @Autowired
   public BoardRenameValidator(BoardSnapshotFinder boardSnapshotFinder) {
      this.boardSnapshotFinder = boardSnapshotFinder;
   }

   @Override
   public boolean supports(Class<?> clazz) {
      return BoardRename.class.isAssignableFrom(clazz);
   }

   @Override
   public void customValidation(Object target, Errors errors) {
      BoardRename boardRename = (BoardRename) target;

      if (boardWithNameAlreadyExists(boardRename)) {
         errors.rejectValue("name", "BoardWithSuchNameAlreadyExists");
      }
   }

   private boolean boardWithNameAlreadyExists(BoardRename boardRename) {
	  BoardSnapshot boardSnapshot = boardSnapshotFinder.findOneByName(boardRename.getName());
      return boardSnapshot != null && !boardSnapshot.getId().equals(boardRename.getId());
   }
}
