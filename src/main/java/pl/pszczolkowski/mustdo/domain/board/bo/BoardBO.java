package pl.pszczolkowski.mustdo.domain.board.bo;

import pl.pszczolkowski.mustdo.domain.board.dto.BoardSnapshot;

public interface BoardBO {

   BoardSnapshot add(String name, Long teamId);

   BoardSnapshot rename(Long id, String name);

   void delete(Long id);
   
   void markAsPublic(Long id);
   
   void markAsPrivate(Long id);
}
