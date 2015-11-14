package pl.pszczolkowski.mustdo.domain.user.finder;

import pl.pszczolkowski.mustdo.domain.user.dto.UserSnapshot;

public interface UserSnapshotFinder {

   UserSnapshot findById(Long id);

   UserSnapshot findByLogin(String login);

}
