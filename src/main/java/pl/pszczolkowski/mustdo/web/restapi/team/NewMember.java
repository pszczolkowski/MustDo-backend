package pl.pszczolkowski.mustdo.web.restapi.team;

import javax.validation.constraints.NotNull;

public class NewMember {
	@NotNull
	private Long teamId;
	
	@NotNull
	private Long userId;

	public Long getTeamId() {
		return teamId;
	}
	
	public Long getUserId() {
		return userId;
	}
}
