package pl.pszczolkowski.mustdo.domain.team.bo;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import pl.pszczolkowski.mustdo.domain.team.dto.TeamSnapshot;
import pl.pszczolkowski.mustdo.domain.team.enty.Team;
import pl.pszczolkowski.mustdo.domain.team.repository.TeamRepository;
import pl.pszczolkowski.mustdo.sharedkernel.annotations.BussinesObject;

@BussinesObject
public class TeamBOImpl
   implements TeamBO {

   private static final Logger LOGGER = LoggerFactory.getLogger(TeamBOImpl.class);

   private final TeamRepository teamRepository;

   @Autowired
   public TeamBOImpl(TeamRepository teamRepository) {
      this.teamRepository = teamRepository;
   }

   @Override
   public TeamSnapshot add(String name, Long createdBy) {
      Team team = new Team(name, createdBy);
      team = teamRepository.save(team);
      TeamSnapshot teamSnapshot = team.toSnapshot();
      LOGGER.info("Created new Team with id <{}> and name <{}>", teamSnapshot.getId(), teamSnapshot.getName());
      return teamSnapshot;
   }

   @Override
   public TeamSnapshot add(String name, Long createdBy, Collection<Long> teamMembersIds) {
      Team team = new Team(name, createdBy, teamMembersIds);
      team = teamRepository.save(team);
      TeamSnapshot teamSnapshot = team.toSnapshot();
      LOGGER.info("Created new Team with id <{}> and name <{}>", teamSnapshot.getId(), teamSnapshot.getName());
      return teamSnapshot;
   }

   @Override
   public void addTeamMember(Long teamId, Long newTeamMemberId) {
      Team team = teamRepository.findOne(teamId);
      team.addTeamMember(newTeamMemberId);
      teamRepository.save(team);
      
      LOGGER.info("Added new team member with id <{}> to Team with id <{}>", newTeamMemberId, teamId);
   }

   @Override
   public void removeTeamMember(Long teamId, Long teamMemberId) {
      Team team = teamRepository.findOne(teamId);
      team.removeTeamMember(teamMemberId);
      teamRepository.save(team);
      
      LOGGER.info("Removed team member with id <{}> from Team with id <{}>", teamMemberId, teamId);
   }

}
