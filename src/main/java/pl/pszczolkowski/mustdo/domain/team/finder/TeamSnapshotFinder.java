package pl.pszczolkowski.mustdo.domain.team.finder;

import java.util.List;

import pl.pszczolkowski.mustdo.domain.team.dto.TeamSnapshot;

public interface TeamSnapshotFinder {

   List<TeamSnapshot> findAllByUserId(Long userId);
}
