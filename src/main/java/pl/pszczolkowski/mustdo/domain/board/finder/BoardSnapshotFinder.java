package pl.pszczolkowski.mustdo.domain.board.finder;

import java.util.List;

import pl.pszczolkowski.mustdo.domain.board.dto.BoardSnapshot;

public interface BoardSnapshotFinder {

   List<BoardSnapshot> findAll();

   BoardSnapshot findOneById(Long id);

   BoardSnapshot findOneByName(String name);
}
