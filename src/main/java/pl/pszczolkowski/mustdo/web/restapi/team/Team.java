package pl.pszczolkowski.mustdo.web.restapi.team;

import java.util.List;

import pl.pszczolkowski.mustdo.domain.team.dto.TeamSnapshot;

public class Team {
	
	private Long id;
	private String name;
	private List<Member> members;
	
	public Team(TeamSnapshot teamSnapshot) {
		this.id = teamSnapshot.getId();
		this.name = teamSnapshot.getName();
	}
	
	public Team(TeamSnapshot teamSnapshot, List<Member> members) {
		this(teamSnapshot);
		this.members = members;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public List<Member> getMembers() {
		return members;
	}
}
