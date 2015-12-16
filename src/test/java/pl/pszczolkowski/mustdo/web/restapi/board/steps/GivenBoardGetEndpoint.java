package pl.pszczolkowski.mustdo.web.restapi.board.steps;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import pl.pszczolkowski.mustdo.domain.board.bo.BoardBO;
import pl.pszczolkowski.mustdo.domain.board.dto.BoardSnapshot;
import pl.pszczolkowski.mustdo.domain.team.bo.TeamBO;
import pl.pszczolkowski.mustdo.domain.team.dto.TeamSnapshot;
import pl.pszczolkowski.mustdo.domain.user.dto.UserSnapshot;
import pl.pszczolkowski.mustdo.domain.user.entity.User;
import pl.pszczolkowski.mustdo.domain.user.repo.UserRepository;

public class GivenBoardGetEndpoint extends Stage<GivenBoardGetEndpoint> {

	private static final String CLAZZ = GivenBoardGetEndpoint.class.getSimpleName();
	@ExpectedScenarioState
	private BoardBO boardBO;
	@ExpectedScenarioState
	private TeamBO teamBO;
	@ExpectedScenarioState
	private UserRepository userRepository;
	
	@ProvidedScenarioState
	private MockHttpServletRequestBuilder request;
	@ProvidedScenarioState
	private BoardSnapshot boardSnapshot;
	private Long id;
	private TeamSnapshot teamSnapshot;
	private UserSnapshot userSnapshot;

	public GivenBoardGetEndpoint a_board() {
		boardSnapshot = boardBO.add(CLAZZ,teamSnapshot.getId());
		this.id = boardSnapshot.getId();
		return this;
	}

	public GivenBoardGetEndpoint a_request_to_endpoint() {
		request = get("/board/{boardId}", id)
				.contentType(MediaType.APPLICATION_JSON);
		return this;
	}

	public GivenBoardGetEndpoint an_invalid_id() {
		this.id = 9999999999l;
		return this;
		
	}

	public GivenBoardGetEndpoint a_team() {
		teamSnapshot = teamBO.add("Team", userSnapshot.getId());
		return this;
	}

	public GivenBoardGetEndpoint an_user_with_name(String username) {
		User user = new User(username, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		userRepository.save(user);
		userSnapshot = user.toSnapshot();
		return this;
	}

}
