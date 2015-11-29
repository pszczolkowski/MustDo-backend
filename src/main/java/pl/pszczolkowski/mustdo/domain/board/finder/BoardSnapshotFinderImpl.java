package pl.pszczolkowski.mustdo.domain.board.finder;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import pl.pszczolkowski.mustdo.domain.board.dto.BoardSnapshot;
import pl.pszczolkowski.mustdo.domain.board.entity.Board;
import pl.pszczolkowski.mustdo.domain.board.repository.BoardRepository;
import pl.pszczolkowski.mustdo.sharedkernel.annotations.Finder;

@Finder
public class BoardSnapshotFinderImpl
   implements BoardSnapshotFinder {

   private final BoardRepository boardRepository;

   @Autowired
   public BoardSnapshotFinderImpl(BoardRepository boardRepository) {
      this.boardRepository = boardRepository;
   }

   @Override
   public List<BoardSnapshot> findAll() {
      return boardRepository.findAll()
         .stream()
         .map(Board::toSnapshot)
         .collect(Collectors.toList());
   }

   @Override
   public BoardSnapshot findOneById(Long id) {
      Board board = boardRepository.findOne(id);

      return board == null ? null : board.toSnapshot();
   }

   @Override
   public BoardSnapshot findOneByName(String name) {
      Board board = boardRepository.findOneByName(name);

      return board == null ? null : board.toSnapshot();
   }

   @Override
   public List<BoardSnapshot> findAllByTeamId(Long teamId) {
      return boardRepository.findAllByTeamId(teamId)
         .stream()
         .map(Board::toSnapshot)
         .collect(Collectors.toList());
   }
   @Override
   public List<BoardSnapshot> findAllByTeamIdIn(Collection<Long> ids) {
      return boardRepository.findAllByTeamIdIn(ids)
         .stream()
         .map(Board::toSnapshot)
         .collect(Collectors.toList());
   }

}
