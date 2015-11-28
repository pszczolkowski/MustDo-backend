package pl.pszczolkowski.mustdo.domain.team.enty;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import pl.pszczolkowski.mustdo.domain.team.dto.TeamSnapshot;
import pl.pszczolkowski.mustdo.sharedkernel.exception.EntityInStateNewException;

@Entity
public class Team
   implements Serializable {

   private static final long serialVersionUID = 3633631842730821582L;

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;

   @NotNull
   @Size(min = 3,
      max = 20)
   private String name;

   @NotEmpty
   @ElementCollection(fetch = FetchType.EAGER)
   private Set<Long> teamMembersIds = new HashSet<>();

   protected Team() {
   }

   public Team(String name, Long createdBy) {
      this.name = name;
      this.teamMembersIds.add(createdBy);
   }

   public Team(String name, Long createdBy, Collection<Long> teamMembersIds) {
      this.name = name;
      this.teamMembersIds.add(createdBy);
      this.teamMembersIds.addAll(teamMembersIds);
   }

   public void addTeamMember(Long newTeamMemberId) {
      this.teamMembersIds.add(newTeamMemberId);
   }

   public void removeTeamMember(Long teamMemberId) {
      this.teamMembersIds.remove(teamMemberId);
   }

   public TeamSnapshot toSnapshot() {
      if (id == null) {
         throw new EntityInStateNewException();
      }
      return new TeamSnapshot(id, name, teamMembersIds);
   }

}
