package pl.pszczolkowski.mustdo.web.restapi.board.steps;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import pl.pszczolkowski.mustdo.domain.board.bo.BoardBO;
import pl.pszczolkowski.mustdo.domain.board.dto.BoardSnapshot;

public class GivenBoardDeleteEndpoint extends Stage<GivenBoardDeleteEndpoint>{
	private static final String CLAZZ = GivenBoardDeleteEndpoint.class.getSimpleName();
	
	@ExpectedScenarioState
	private BoardBO boardBO;
	
	@ProvidedScenarioState
	private BoardSnapshot boardSnapshot;
	@ProvidedScenarioState
	private MockHttpServletRequestBuilder request;
	
	public GivenBoardDeleteEndpoint a_board() {
		boardSnapshot = boardBO.add(CLAZZ);
		return this;
	}

	public GivenBoardDeleteEndpoint a_request_to_endpoint() {
		request = delete("/board/{boardId}", boardSnapshot.getId())
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8"));
		return this;
	}

}
