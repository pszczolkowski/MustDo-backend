package pl.pszczolkowski.mustdo.web.restapi.team;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.pszczolkowski.mustdo.domain.board.dto.BoardSnapshot;
import pl.pszczolkowski.mustdo.domain.board.finder.BoardSnapshotFinder;
import pl.pszczolkowski.mustdo.domain.team.bo.TeamBO;
import pl.pszczolkowski.mustdo.domain.team.dto.TeamSnapshot;
import pl.pszczolkowski.mustdo.domain.team.finder.TeamSnapshotFinder;
import pl.pszczolkowski.mustdo.domain.user.dto.UserSnapshot;
import pl.pszczolkowski.mustdo.domain.user.finder.UserSnapshotFinder;

@RestController
@RequestMapping("/team")
public class TeamApi {
	private final TeamSnapshotFinder teamSnapshotFinder;
	private final TeamBO teamBO;
	private final UserSnapshotFinder userSnapshotFinder;
	private final BoardSnapshotFinder boardSnapshotFinder;
	
	@Autowired
	public TeamApi(TeamSnapshotFinder teamSnapshotFinder, TeamBO teamBO, UserSnapshotFinder userSnapshotFinder, BoardSnapshotFinder boardSnapshotFinder){
		this.boardSnapshotFinder = boardSnapshotFinder;
		this.teamSnapshotFinder = teamSnapshotFinder;
		this.teamBO = teamBO;
		this.userSnapshotFinder = userSnapshotFinder;
	}
	
	private UserSnapshot getLoggedUserSnapshot() {
		String login = SecurityContextHolder.getContext().getAuthentication().getName();
		UserSnapshot userSnapshot = userSnapshotFinder.findByLogin(login);
		return userSnapshot;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public HttpEntity<List<Team>> get(){
		UserSnapshot userSnapshot = getLoggedUserSnapshot();
		List<TeamSnapshot> teamSnapshots = teamSnapshotFinder.findAllByUserId(userSnapshot.getId());
		
		List<Team> teams = teamSnapshots
				.stream()
				.map(Team::new)
				.collect(Collectors.toList());
		
		return ResponseEntity
				.ok()
				.body(teams);
		
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public HttpEntity<Team> get(@PathVariable("id") Long teamId){
		TeamSnapshot teamSnapshot = teamSnapshotFinder.findById(teamId);
		if(teamSnapshot == null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		if(!isCurrentLoggedUserTeamMember(teamSnapshot)){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		
		List<Member> members = getMemberSnapshot(teamSnapshot.getTeamMembersIds());
		Team team = new Team(teamSnapshot, members);
		return ResponseEntity
				.ok()
				.body(team);
	}
	@RequestMapping(params = {"boardId"}, method = RequestMethod.GET)
	public HttpEntity<Team> getByBoardId(@RequestParam("boardId") Long boardId){
      BoardSnapshot boardSnapshot = boardSnapshotFinder.findOneById(boardId);
      if(boardSnapshot == null ){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
		TeamSnapshot teamSnapshot = teamSnapshotFinder.findById(boardSnapshot.getTeamId());
		
		if(!isCurrentLoggedUserTeamMember(teamSnapshot)){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		
		List<Member> members = getMemberSnapshot(teamSnapshot.getTeamMembersIds());
		Team team = new Team(teamSnapshot, members);
		return ResponseEntity
				.ok()
				.body(team);
	}
	
	private List<Member> getMemberSnapshot(Set<Long> teamMembersIds) {
		List<Member> members = new ArrayList<>();
		for(Long id : teamMembersIds){
			UserSnapshot userSnapshot = userSnapshotFinder.findById(id);
			Member member = new Member(userSnapshot.getId(), userSnapshot.getLogin());
			members.add(member);
		}
		return members;
	}

	private boolean isCurrentLoggedUserTeamMember(TeamSnapshot teamSnapshot) {
		UserSnapshot userSnapshot = getLoggedUserSnapshot();
		
		return teamSnapshot.getTeamMembersIds()
				.stream()
				.anyMatch(id -> id.equals(userSnapshot.getId()));
	}

	@RequestMapping(method = RequestMethod.POST)
	public HttpEntity<Team> add(@Valid @RequestBody TeamNew teamNew){
		UserSnapshot userSnapshot = getLoggedUserSnapshot();
		TeamSnapshot teamSnapshot = teamBO.add(teamNew.getName(), userSnapshot.getId());
		
		return ResponseEntity
				.ok()
				.body(new Team(teamSnapshot));
	}
	
	@RequestMapping(value = "/member", method = RequestMethod.POST)
	public HttpEntity<Void> addMember(@Valid @RequestBody NewMember newMember) {
		UserSnapshot userSnapshot = userSnapshotFinder.findById(newMember.getUserId());
		if (userSnapshot == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
		}
		
		TeamSnapshot teamSnapshot = teamSnapshotFinder.findById(newMember.getTeamId());
		if(isUserTeamMember(userSnapshot, teamSnapshot)){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		teamBO.addTeamMember(newMember.getTeamId(), userSnapshot.getId());
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	private boolean isUserTeamMember(UserSnapshot userSnapshot, TeamSnapshot teamSnapshot) {
		return teamSnapshot.getTeamMembersIds()
				.stream()
				.anyMatch(id -> id.equals(userSnapshot.getId()));
	}

	@RequestMapping(value = "/{teamId}/member/{userId}", method = RequestMethod.DELETE)
	public HttpEntity<Void> deleteMember(@PathVariable("teamId") Long teamId, @PathVariable("userId") Long userId){
		
		TeamSnapshot teamSnapshot = teamSnapshotFinder.findById(teamId);
		UserSnapshot userSnapshot = userSnapshotFinder.findById(userId);
		
		if(teamSnapshot != null && userSnapshot!=null && isUserTeamMember(userSnapshot, teamSnapshot)){
			teamBO.removeTeamMember(teamId, userId);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
