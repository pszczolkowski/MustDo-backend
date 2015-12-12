package pl.pszczolkowski.mustdo.web.restapi.team;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class Member {

	@NotNull
	private Long id;
	
	@NotEmpty
	private String login;
	
	public Member(Long id, String login) {
		this.id = id;
		this.login = login;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getLogin() {
		return login;
	}
}
