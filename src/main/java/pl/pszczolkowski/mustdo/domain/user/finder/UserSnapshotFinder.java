package pl.pszczolkowski.mustdo.domain.user.finder;

import java.util.List;
import java.util.Map;
import java.util.Set;

import pl.pszczolkowski.mustdo.domain.user.dto.UserSnapshot;

public interface UserSnapshotFinder {

   UserSnapshot findById(Long id);

   UserSnapshot findByLogin(String login);
   
   public Map<Long, UserSnapshot> findAllAsMap(Set<Long> ids);

   List<UserSnapshot> findAll();
}
