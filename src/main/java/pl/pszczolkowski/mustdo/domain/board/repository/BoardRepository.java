package pl.pszczolkowski.mustdo.domain.board.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.pszczolkowski.mustdo.domain.board.entity.Board;

public interface BoardRepository
   extends JpaRepository<Board, Long> {

   Board findOneByName(String name);
   
   List<Board> findAllByTeamId(Long teamId);
   
   List<Board> findAllByTeamIdIn(Collection<Long> teamIds);

}
