package pl.pszczolkowski.mustdo.web.restapi.user;

import pl.pszczolkowski.mustdo.domain.user.dto.UserSnapshot;

public class User {
	private Long id;
	private String login;
	
	public User(UserSnapshot userSnapshot) {
		this.id = userSnapshot.getId();
		this.login = userSnapshot.getLogin();
	}
	
	public Long getId() {
		return id;
	}
	
	public String getLogin() {
		return login;
	}
}
