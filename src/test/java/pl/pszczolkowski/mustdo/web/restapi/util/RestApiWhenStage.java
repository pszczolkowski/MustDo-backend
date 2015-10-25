package pl.pszczolkowski.mustdo.web.restapi.util;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.BeforeStage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

public class RestApiWhenStage extends Stage<RestApiWhenStage> {

	@ProvidedScenarioState
	private ResultActions result;

	@ExpectedScenarioState
	private WebApplicationContext context;
	@ExpectedScenarioState
	private MockHttpServletRequestBuilder request;

	private MockMvc mockMvc;

	@BeforeStage
	public void setUp() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
	}

	public void request_is_invoked() throws Exception {
		result = mockMvc.perform(request);
	}

}
