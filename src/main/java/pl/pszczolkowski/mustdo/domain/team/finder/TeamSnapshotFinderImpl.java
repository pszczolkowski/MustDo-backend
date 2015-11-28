package pl.pszczolkowski.mustdo.domain.team.finder;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import pl.pszczolkowski.mustdo.domain.team.dto.TeamSnapshot;
import pl.pszczolkowski.mustdo.domain.team.enty.Team;
import pl.pszczolkowski.mustdo.domain.team.repository.TeamRepository;
import pl.pszczolkowski.mustdo.sharedkernel.annotations.Finder;

@Finder
public class TeamSnapshotFinderImpl
   implements TeamSnapshotFinder {

   private final TeamRepository teamRepositry;

   @Autowired
   public TeamSnapshotFinderImpl(TeamRepository teamRepository) {
      this.teamRepositry = teamRepository;
   }

   @Override
   public List<TeamSnapshot> findAllByUserId(Long userId) {
      return teamRepositry
         .findAllByUserId(userId)
         .stream()
         .map(Team::toSnapshot)
         .collect(Collectors.toList());
   }

}
