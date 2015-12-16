package pl.pszczolkowski.mustdo.web.restapi.board.steps;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import pl.pszczolkowski.mustdo.domain.board.bo.BoardBO;
import pl.pszczolkowski.mustdo.domain.board.dto.BoardSnapshot;
import pl.pszczolkowski.mustdo.domain.team.bo.TeamBO;
import pl.pszczolkowski.mustdo.domain.team.dto.TeamSnapshot;
import pl.pszczolkowski.mustdo.domain.user.bo.UserBO;
import pl.pszczolkowski.mustdo.domain.user.entity.User;
import pl.pszczolkowski.mustdo.domain.user.repo.UserRepository;

public class GivenBoardDeleteEndpoint extends Stage<GivenBoardDeleteEndpoint>{
	private static final String CLAZZ = "Team";
	
	@ExpectedScenarioState
	private BoardBO boardBO;
	
	@ProvidedScenarioState
	private BoardSnapshot boardSnapshot;
	@ProvidedScenarioState
	private MockHttpServletRequestBuilder request;
	@ProvidedScenarioState
	private TeamBO teamBO;
	@ProvidedScenarioState
	private UserRepository userRepository;
	private TeamSnapshot teamSnapshot;
	
	public GivenBoardDeleteEndpoint a_board() {
		boardSnapshot = boardBO.add(CLAZZ,teamSnapshot.getId());
		return this;
	}

	public GivenBoardDeleteEndpoint a_request_to_endpoint() {
		request = delete("/board/{boardId}", boardSnapshot.getId())
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8"));
		return this;
	}

	public GivenBoardDeleteEndpoint a_team() {
		teamSnapshot = teamBO.add(CLAZZ, 1l);
		return this;
	}

	public GivenBoardDeleteEndpoint a_user_with_name(String username) { 
		User user = new User(username, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		userRepository.save(user);
		return this;
	}

}
