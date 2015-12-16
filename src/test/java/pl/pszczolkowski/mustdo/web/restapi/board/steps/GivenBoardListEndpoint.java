package pl.pszczolkowski.mustdo.web.restapi.board.steps;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import pl.pszczolkowski.mustdo.domain.board.bo.BoardBO;
import pl.pszczolkowski.mustdo.domain.team.bo.TeamBO;
import pl.pszczolkowski.mustdo.domain.team.dto.TeamSnapshot;
import pl.pszczolkowski.mustdo.domain.user.dto.UserSnapshot;
import pl.pszczolkowski.mustdo.domain.user.entity.User;
import pl.pszczolkowski.mustdo.domain.user.repo.UserRepository;

public class GivenBoardListEndpoint extends Stage<GivenBoardListEndpoint>{
	private static final String CLAZZ = GivenBoardListEndpoint.class.getSimpleName();

	@ExpectedScenarioState
	private BoardBO boardBO;
	@ExpectedScenarioState
	private TeamBO teamBO;
	
	@ProvidedScenarioState
	private MockHttpServletRequestBuilder request;

	private UserSnapshot userSnapshot;
	@ProvidedScenarioState
	private UserRepository userRepository;
	
	public GivenBoardListEndpoint a_board_with_name(String name) {
		TeamSnapshot teamSnapshot = teamBO.add("Team", userSnapshot.getId());
		boardBO.add(name,teamSnapshot.getId());
		return this;
	}

	public GivenBoardListEndpoint a_request_to_endpoint() {
		request = get("/board")
	            .accept(MediaType.APPLICATION_JSON);
		return this;
	}

	public GivenBoardListEndpoint list_of_boards() {
		a_board_with_name(CLAZZ);
		a_board_with_name(CLAZZ+"1");
		a_board_with_name(CLAZZ+"2");
		return this;
	}
	
	public GivenBoardListEndpoint an_user_with_name(String username) {
		User user = new User(username, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		userRepository.save(user);
		userSnapshot = user.toSnapshot();
		return this;
	}

}
