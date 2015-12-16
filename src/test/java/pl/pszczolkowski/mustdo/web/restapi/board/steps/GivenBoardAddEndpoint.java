package pl.pszczolkowski.mustdo.web.restapi.board.steps;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import pl.pszczolkowski.mustdo.config.OAuthHelper;
import pl.pszczolkowski.mustdo.domain.board.bo.BoardBO;
import pl.pszczolkowski.mustdo.domain.team.bo.TeamBO;
import pl.pszczolkowski.mustdo.domain.team.dto.TeamSnapshot;
import pl.pszczolkowski.mustdo.web.restapi.board.BoardNew;

public class GivenBoardAddEndpoint extends Stage<GivenBoardAddEndpoint>{
	
	@ProvidedScenarioState
	private String name;
	@ProvidedScenarioState
	private BoardNew boardNew;
	@ProvidedScenarioState
	private MockHttpServletRequestBuilder request;
	
	@ExpectedScenarioState
	private BoardBO boardBO;
	@ExpectedScenarioState
	private TeamBO teamBO;
	private TeamSnapshot teamSnapshot;

	public GivenBoardAddEndpoint a_name_for_board(String name) {
		this.name = name;
		return this;
	}

	public GivenBoardAddEndpoint a_request_to_endpoint() throws JsonProcessingException {
		boardNew = new BoardNew();
		boardNew.setName("Board");
		boardNew.setExistingTeamId(teamSnapshot.getId());
		
		aRequestToEndpoint();
		
		return this;
		
	}

	private void aRequestToEndpoint() throws JsonProcessingException {
			ObjectMapper objectMapper = new ObjectMapper();

	      String body = objectMapper.writeValueAsString(boardNew);

	      request = post("/board")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(body)
	            .accept(MediaType.parseMediaType("application/json;charset=UTF-8"));
		
	}

	public GivenBoardAddEndpoint a_board_with_name(String name) {
		boardBO.add("Board",teamSnapshot.getId());
		return this;
	}

	public GivenBoardAddEndpoint a_team() {
		teamSnapshot = teamBO.add("Team", 1l);
		return this;
	}
	
	

}
