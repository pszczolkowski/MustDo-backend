package pl.pszczolkowski.mustdo.domain.team.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pl.pszczolkowski.mustdo.domain.team.enty.Team;

public interface TeamRepository
   extends JpaRepository<Team, Long> {
   
   @Query("SELECT t FROM Team t INNER JOIN t.teamMembersIds m WHERE m = :userId")
   List<Team> findAllByUserId( @Param("userId") Long userId);

}
