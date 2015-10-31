package pl.pszczolkowski.mustdo.domain.board.event;

import org.springframework.context.ApplicationEvent;

public class BoardRemovedEvent
   extends ApplicationEvent {

   private final Long boardId;

   public BoardRemovedEvent(Object source, Long boardId) {
      super(source);
      this.boardId = boardId;
   }

   public Long getBoardId() {
      return boardId;
   }

}
