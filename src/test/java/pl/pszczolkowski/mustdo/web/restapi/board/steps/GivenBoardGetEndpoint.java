package pl.pszczolkowski.mustdo.web.restapi.board.steps;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import pl.pszczolkowski.mustdo.domain.board.bo.BoardBO;
import pl.pszczolkowski.mustdo.domain.board.dto.BoardSnapshot;

public class GivenBoardGetEndpoint extends Stage<GivenBoardGetEndpoint> {

	private static final String CLAZZ = GivenBoardGetEndpoint.class.getSimpleName();
	@ExpectedScenarioState
	private BoardBO boardBO;
	
	@ProvidedScenarioState
	private MockHttpServletRequestBuilder request;
	@ProvidedScenarioState
	private BoardSnapshot boardSnapshot;
	private Long id;

	public GivenBoardGetEndpoint a_board() {
		boardSnapshot = boardBO.add(CLAZZ);
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

}
