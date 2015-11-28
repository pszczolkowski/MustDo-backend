package pl.pszczolkowski.mustdo.domain.team.bo;

import java.util.Collection;

import pl.pszczolkowski.mustdo.domain.team.dto.TeamSnapshot;

public interface TeamBO {

   TeamSnapshot add(String name, Long createdBy);

   TeamSnapshot add(String name, Long createdBy, Collection<Long> teamMembersIds);

   void addTeamMember(Long teamId, Long newTeamMemberId);

   void removeTeamMember(Long teamId, Long teamMemberId);

}
