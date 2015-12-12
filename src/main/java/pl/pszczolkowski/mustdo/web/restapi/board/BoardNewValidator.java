package pl.pszczolkowski.mustdo.web.restapi.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

import pl.pszczolkowski.mustdo.domain.board.finder.BoardSnapshotFinder;
import pl.pszczolkowski.mustdo.domain.team.finder.TeamSnapshotFinder;
import pl.pszczolkowski.mustdo.sharedkernel.annotations.RestValidator;
import pl.pszczolkowski.mustdo.web.restapi.commonvalidation.AbstractValidator;

@RestValidator
public class BoardNewValidator
   extends AbstractValidator {

   private final BoardSnapshotFinder boardSnapshotFinder;
   private final TeamSnapshotFinder teamSnapshotFinder;

   @Autowired
   public BoardNewValidator(BoardSnapshotFinder boardSnapshotFinder, TeamSnapshotFinder teamSnapshotFinder) {
      this.boardSnapshotFinder = boardSnapshotFinder;
      this.teamSnapshotFinder = teamSnapshotFinder;
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
      
      if(teamIsNotSpecified(boardNew)){
         errors.rejectValue("teamName", "BoardTeamIsNotSet");
      }
      if(!teamIdIsNull(boardNew)){
         if(!teamWithGivenIdExist(boardNew)){
            errors.rejectValue("teamName", "TeamWithGivenIdDoesNotExists");
         }
      }
   }

   private boolean teamWithGivenIdExist(BoardNew boardNew) {
      return teamSnapshotFinder.findById(boardNew.getExistingTeamId())!=null;
   }

   private static boolean teamIsNotSpecified(BoardNew boardNew) {
      return teamIdIsNull(boardNew) && newTeamNameIsNull(boardNew);
   }

   private static boolean newTeamNameIsNull(BoardNew boardNew) {
      return boardNew.getTeamName() == null;
   }

   private static boolean teamIdIsNull(BoardNew boardNew) {
      return boardNew.getExistingTeamId() == null;
   }

   private boolean boardWithNameAlreadyExists(String name) {
      return boardSnapshotFinder.findOneByName(name) != null;
   }
}
