package pl.pszczolkowski.mustdo.domain.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.pszczolkowski.mustdo.domain.board.entity.Board;

public interface BoardRepository
   extends JpaRepository<Board, Long> {

   Board findOneByName(String name);

}
