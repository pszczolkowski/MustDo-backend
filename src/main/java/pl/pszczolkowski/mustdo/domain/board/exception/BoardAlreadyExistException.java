package pl.pszczolkowski.mustdo.domain.board.exception;

public class BoardAlreadyExistException
   extends RuntimeException {

   private static final long serialVersionUID = -1794537307362685773L;

   public BoardAlreadyExistException() {
      super("Board with such name already exists");
   }
}
