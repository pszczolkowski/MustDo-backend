package pl.pszczolkowski.mustdo.web.restapi.account;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;

public class AccountRegister {

	@NotNull
	@Size(min = 3, max = 20)
	@Pattern(regexp = "^[a-z0-9]*$")
	private String login;

	@NotNull
	@Size(min = 5, max = 30)
	private String password;

	@ApiModelProperty(value = "Username for new account")
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@ApiModelProperty(value = "Password for new account")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
