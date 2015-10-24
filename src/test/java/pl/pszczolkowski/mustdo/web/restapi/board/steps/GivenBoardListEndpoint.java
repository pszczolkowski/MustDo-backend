package pl.pszczolkowski.mustdo.web.restapi.board.steps;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import pl.pszczolkowski.mustdo.domain.board.bo.BoardBO;

public class GivenBoardListEndpoint extends Stage<GivenBoardListEndpoint>{
	@ExpectedScenarioState
	private BoardBO boardBO;
	
	@ProvidedScenarioState
	private MockHttpServletRequestBuilder request;
	
	public GivenBoardListEndpoint a_board_with_name(String name) {
		boardBO.add(name);
		return this;
	}

	public GivenBoardListEndpoint a_request_to_endpoint() {
		request = get("/board")
	            .accept(MediaType.APPLICATION_JSON);
		return this;
	}

}
