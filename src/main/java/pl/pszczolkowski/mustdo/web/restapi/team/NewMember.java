package pl.pszczolkowski.mustdo.web.restapi.team;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class NewMember {
	@NotNull
	private Long teamId;
	
	@NotEmpty
	private String login;
	
	public String getLogin() {
		return login;
	}
	
	public Long getTeamId() {
		return teamId;
	}
}
