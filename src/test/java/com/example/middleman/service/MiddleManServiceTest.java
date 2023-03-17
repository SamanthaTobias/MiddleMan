package com.example.middleman.service;

import com.example.middleman.Effect;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class MiddleManServiceTest {

	private MiddleManService middleManService;
	private MockRestServiceServer mockServer;

	@BeforeEach
	public void setup() {
		RestTemplate restTemplate = new RestTemplate();
		middleManService = new MiddleManService(restTemplate, "http://basic-app");
		mockServer = MockRestServiceServer.createServer(restTemplate);
	}

	@Test
	public void testSetName() {
		String name = "John";
		mockServer.expect(requestTo("http://basic-app/setName/" + name))
				.andExpect(method(HttpMethod.POST))
				.andRespond(withSuccess());

		middleManService.setName(name);
		mockServer.verify();
	}

	@Test
	public void testSetEffect() {
		middleManService.setEffect("uppercase");
		assertThat(middleManService.getEffect()).isEqualTo(Effect.UPPERCASE);
	}

	@Test
	public void testGetHelloMessage() {
		String helloMessage = "Hello, John!";
		mockServer.expect(requestTo("http://basic-app/hello"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withSuccess(helloMessage, MediaType.TEXT_PLAIN));

		middleManService.setEffect("uppercase");
		String result = middleManService.getHelloMessage();
		assertThat(result).isEqualTo(helloMessage.toUpperCase());
		mockServer.verify();
	}

	@Test
	public void testGetHelloMessageWithLowercaseEffect() {
		String helloMessage = "Hello, John!";
		mockServer.expect(requestTo("http://basic-app/hello"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withSuccess(helloMessage, MediaType.TEXT_PLAIN));

		middleManService.setEffect("lowercase");
		String result = middleManService.getHelloMessage();
		assertThat(result).isEqualTo(helloMessage.toLowerCase());
		mockServer.verify();
	}

	@Test
	public void testGetHelloMessageWithRandomCaseEffect() {
		String helloMessage = "Hello, John!";
		mockServer.expect(requestTo("http://basic-app/hello"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withSuccess(helloMessage, MediaType.TEXT_PLAIN));

		middleManService.setEffect("randomcase");
		String result = middleManService.getHelloMessage();
		assertThat(result).isNotEqualTo(helloMessage);
		assertThat(result.length()).isEqualTo(helloMessage.length());
		mockServer.verify();
	}

	@Test
	public void testGetHelloMessageWithAlphabetizeEffect() {
		String helloMessage = "The quick brown fox jumps over the lazy dog";
		mockServer.expect(requestTo("http://basic-app/hello"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withSuccess(helloMessage, MediaType.TEXT_PLAIN));

		middleManService.setEffect("alphabetize");
		String result = middleManService.getHelloMessage();
		assertThat(result).isEqualTo("brown dog fox jumps lazy over quick The the");
		mockServer.verify();
	}

	@Test
	public void testGetHelloMessageWithNoEffect() {
		String helloMessage = "Hello, John!";
		mockServer.expect(requestTo("http://basic-app/hello"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withSuccess(helloMessage, MediaType.TEXT_PLAIN));

		middleManService.setEffect("none");
		String result = middleManService.getHelloMessage();
		assertThat(result).isEqualTo(helloMessage);
		mockServer.verify();
	}

}
