package pl.pszczolkowski.mustdo.web.restapi.board.steps;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import pl.pszczolkowski.mustdo.domain.board.bo.BoardBO;
import pl.pszczolkowski.mustdo.domain.board.dto.BoardSnapshot;
import pl.pszczolkowski.mustdo.web.restapi.board.BoardRename;

public class GivenBoardRenameEndpoint extends Stage<GivenBoardRenameEndpoint>{
	
	@ProvidedScenarioState
	private String name;
	@ProvidedScenarioState
	private BoardSnapshot boardSnapshot;
	@ProvidedScenarioState
	private MockHttpServletRequestBuilder request;
	@ProvidedScenarioState
	private BoardRename boardRename;
	
	@ExpectedScenarioState
	private BoardBO boardBO;
	
	public GivenBoardRenameEndpoint a_board_with_name(String name) {
		boardSnapshot = boardBO.add(name);
		return this;
	}

	public GivenBoardRenameEndpoint new_name_for_board(String name) {
		this.name = name;
		return this;
		
	}

	public GivenBoardRenameEndpoint request_to_endpoint() throws JsonProcessingException {
		boardRename = new BoardRename();
		boardRename.setName(name);
		boardRename.setId(boardSnapshot.getId());
		
		aRequestToEndpoint();
		
		return this;
	}
	
	private void aRequestToEndpoint() throws JsonProcessingException {
	      ObjectMapper objectMapper = new ObjectMapper();

	      String body = objectMapper.writeValueAsString(boardRename);

	      request = put("/board")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(body)
	            .accept(MediaType.parseMediaType("application/json;charset=UTF-8"));
	   }

	
	

}
