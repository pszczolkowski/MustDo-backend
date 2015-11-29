package pl.pszczolkowski.mustdo.domain.board.exception;

public class TeamNotExistException
   extends RuntimeException {

   private static final long serialVersionUID = -1794537307362685773L;

   public TeamNotExistException() {
      super("Team with such id doesn't exist");
   }
}
