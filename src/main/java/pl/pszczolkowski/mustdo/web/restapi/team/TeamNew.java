package pl.pszczolkowski.mustdo.web.restapi.team;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TeamNew {
	
	@NotNull
	@Size(min = 3, max = 20)
	private String name;
	
	public String getName() {
		return name;
	}
}
