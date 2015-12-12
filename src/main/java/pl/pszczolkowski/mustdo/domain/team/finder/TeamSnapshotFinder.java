package pl.pszczolkowski.mustdo.domain.team.finder;

import java.util.List;

import pl.pszczolkowski.mustdo.domain.team.dto.TeamSnapshot;

public interface TeamSnapshotFinder {
	
   TeamSnapshot findById(Long teamId);
	
   List<TeamSnapshot> findAllByUserId(Long userId);
}
