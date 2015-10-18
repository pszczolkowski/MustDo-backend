package pl.pszczolkowski.mustdo.web.restapi.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

import pl.pszczolkowski.mustdo.domain.board.finder.BoardSnapshotFinder;
import pl.pszczolkowski.mustdo.sharedkernel.annotations.RestValidator;
import pl.pszczolkowski.mustdo.web.restapi.commonvalidation.AbstractValidator;

@RestValidator
public class BoardNewValidator
   extends AbstractValidator {

   private final BoardSnapshotFinder boardSnapshotFinder;

   @Autowired
   public BoardNewValidator(BoardSnapshotFinder boardSnapshotFinder) {
      this.boardSnapshotFinder = boardSnapshotFinder;
   }

   @Override
   public boolean supports(Class<?> clazz) {
      return BoardNew.class.isAssignableFrom(clazz);
   }

   @Override
   public void customValidation(Object target, Errors errors) {
      BoardNew boardNew = (BoardNew) target;

      if (boardWithNameAlreadyExists(boardNew.getName())) {
         errors.rejectValue("name", "BoardWithSuchNameAlreadyExists");
      }
   }

   private boolean boardWithNameAlreadyExists(String name) {
      return boardSnapshotFinder.findOneByName(name) != null;
   }
}
