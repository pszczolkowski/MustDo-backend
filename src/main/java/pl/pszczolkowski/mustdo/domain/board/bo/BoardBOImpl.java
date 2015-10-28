package pl.pszczolkowski.mustdo.domain.board.bo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import pl.pszczolkowski.mustdo.domain.board.dto.BoardSnapshot;
import pl.pszczolkowski.mustdo.domain.board.entity.Board;
import pl.pszczolkowski.mustdo.domain.board.event.BoardRemovedEvent;
import pl.pszczolkowski.mustdo.domain.board.exception.BoardAlreadyExistException;
import pl.pszczolkowski.mustdo.domain.board.repository.BoardRepository;
import pl.pszczolkowski.mustdo.sharedkernel.annotations.BussinesObject;

@BussinesObject
public class BoardBOImpl
   implements BoardBO {

   private static final Logger LOGGER = LoggerFactory.getLogger(BoardBOImpl.class);
   private final BoardRepository boardRepository;
   private final ApplicationEventPublisher eventPublisher;

   @Autowired
   public BoardBOImpl(BoardRepository boardRepository, ApplicationEventPublisher eventPublisher) {
      this.boardRepository = boardRepository;
      this.eventPublisher = eventPublisher;
   }

   @Override
   public BoardSnapshot add(String name) {
      if (boardRepository.findOneByName(name) != null) {
         throw new BoardAlreadyExistException();
      }
      Board board = new Board(name);

      board = boardRepository.save(board);

      BoardSnapshot boardSnapshot = board.toSnapshot();

      LOGGER.info("Board with id <{}> and name <{}> added", boardSnapshot.getId(), boardSnapshot.getName());

      return boardSnapshot;
   }

   @Override
   public BoardSnapshot rename(Long id, String name) {
	  Board board = boardRepository.findOneByName(name);
	  if (board != null) {
		  BoardSnapshot boardSnapshot = board.toSnapshot();
		  if (boardSnapshot.getId().equals(id)) {
			  return boardSnapshot;
		  } else {
			  throw new BoardAlreadyExistException();
		  }
	  }
	  
      board = boardRepository.findOne(id);
      board.rename(name);
      board = boardRepository.save(board);
      
      BoardSnapshot boardSnapshot = board.toSnapshot();
      LOGGER.info("Board with id <{}> renamed to <{}>", boardSnapshot.getId(), boardSnapshot.getName());
      
      return boardSnapshot;

   }

   @Override
   public void delete(Long id) {
      boardRepository.delete(id);
      LOGGER.info("Board with id <{}> removed", id);
      
      BoardRemovedEvent boardRemovedEvent = new BoardRemovedEvent(this, id);
      eventPublisher.publishEvent(boardRemovedEvent);
   }

}
