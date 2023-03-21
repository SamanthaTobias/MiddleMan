package com.example.middleman.service;

import com.example.middleman.Effect;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MiddleManServiceTest {

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private MiddleManService middleManService;

	@BeforeEach
	public void setup() {
		middleManService.setBasicAppUrl("http://basic-app");
	}

	@Test
	public void testSetName() {
		String name = "John";
		when(restTemplate.postForObject(eq("http://basic-app/setName/" + name), any(), eq(String.class)))
				.thenReturn(null);

		middleManService.setName(name);
		verify(restTemplate).postForObject(eq("http://basic-app/setName/" + name), any(), eq(String.class));
	}

	@Test
	public void testSetEffect() {
		middleManService.setEffect("uppercase");
		assertThat(middleManService.getEffect()).isEqualTo(Effect.UPPERCASE);
	}

	@Test
	public void testGetHelloMessageWithUppercaseEffect() {
		String helloMessage = "Hello, John!";
		when(restTemplate.getForObject(eq("http://basic-app/hello"), eq(String.class)))
				.thenReturn(helloMessage);

		middleManService.setEffect("uppercase");
		String result = middleManService.getHelloMessage();
		assertThat(result).isEqualTo(helloMessage.toUpperCase());
		verify(restTemplate).getForObject(eq("http://basic-app/hello"), eq(String.class));
	}

	@Test
	public void testGetHelloMessageWithLowercaseEffect() {
		String helloMessage = "Hello, John!";
		when(restTemplate.getForObject(eq("http://basic-app/hello"), eq(String.class)))
				.thenReturn(helloMessage);

		middleManService.setEffect("lowercase");
		String result = middleManService.getHelloMessage();
		assertThat(result).isEqualTo(helloMessage.toLowerCase());
		verify(restTemplate).getForObject(eq("http://basic-app/hello"), eq(String.class));
	}

	@Test
	public void testGetHelloMessageWithRandomCaseEffect() {
		String helloMessage = "Hello, John!";
		when(restTemplate.getForObject(eq("http://basic-app/hello"), eq(String.class)))
				.thenReturn(helloMessage);

		middleManService.setEffect("randomcase");
		String result = middleManService.getHelloMessage();
		assertThat(result).isNotEqualTo(helloMessage);
		assertThat(result.length()).isEqualTo(helloMessage.length());
		verify(restTemplate).getForObject(eq("http://basic-app/hello"), eq(String.class));
	}

	@Test
	public void testGetHelloMessageWithAlphabetizeEffect() {
		String helloMessage = "The quick brown fox jumps over the lazy dog";
		when(restTemplate.getForObject(eq("http://basic-app/hello"), eq(String.class)))
				.thenReturn(helloMessage);

		middleManService.setEffect("alphabetize");
		String result = middleManService.getHelloMessage();
		assertThat(result).isEqualTo("brown dog fox jumps lazy over quick The the");
		verify(restTemplate).getForObject(eq("http://basic-app/hello"), eq(String.class));
	}

	@Test
	public void testGetHelloMessageWithNoEffect() {
		String helloMessage = "Hello, John!";
		when(restTemplate.getForObject(eq("http://basic-app/hello"), eq(String.class)))
				.thenReturn(helloMessage);

		middleManService.setEffect("none");
		String result = middleManService.getHelloMessage();
		assertThat(result).isEqualTo(helloMessage);
		verify(restTemplate).getForObject(eq("http://basic-app/hello"), eq(String.class));
	}

	@Test
	public void testReverseString() {
		String input = "hello";
		String expected = "olleh";
		when(restTemplate.getForObject(eq("http://basic-app/reverse?input=" + input), eq(String.class)))
				.thenReturn(expected);

		String result = middleManService.reverseString(input);
		assertThat(result).isEqualTo(expected);
		verify(restTemplate).getForObject(eq("http://basic-app/reverse?input=" + input), eq(String.class));
	}

	@Test
	public void testGetLastReversedString() {
		String expected = "dlrow";
		when(restTemplate.getForObject(eq("http://basic-app/lastReversed"), eq(String.class)))
				.thenReturn(expected);

		String result = middleManService.getLastReversedString();
		assertThat(result).isEqualTo(expected);
		verify(restTemplate).getForObject(eq("http://basic-app/lastReversed"), eq(String.class));
	}
}
