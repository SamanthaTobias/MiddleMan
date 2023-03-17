package com.example.middleman.controller;

import com.example.middleman.service.MiddleManService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MiddleManControllerTest {

	@Mock
	private MiddleManService middleManService;

	@InjectMocks
	private MiddleManController middleManController;

	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(middleManController).build();
	}

	@Test
	public void testSetName() throws Exception {
		String name = "John";
		mockMvc.perform(post("/setName/{name}", name))
				.andExpect(status().isOk())
				.andExpect(content().string("Name set to '" + name + "'"));

		Mockito.verify(middleManService).setName(name);
	}

	@Test
	public void testSetEffect() throws Exception {
		String effect = "bold";
		mockMvc.perform(post("/setEffect/{effect}", effect))
				.andExpect(status().isOk())
				.andExpect(content().string("Effect set to '" + effect + "'"));

		Mockito.verify(middleManService).setEffect(effect);
	}

	@Test
	public void testHello() throws Exception {
		String helloMessage = "Hello, John!";
		Mockito.when(middleManService.getHelloMessage()).thenReturn(helloMessage);

		mockMvc.perform(get("/hello").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(helloMessage));

		Mockito.verify(middleManService).getHelloMessage();
	}

	@Test
	public void testReverseString() {
		String input = "hello";
		String expected = "olleh";
		when(middleManService.reverseString(input)).thenReturn(expected);
		String result = middleManController.reverseString(input);

		assertEquals(expected, result);
	}

	@Test
	public void testGetLastReversedString() {
		String expected = "dlrow";
		when(middleManService.getLastReversedString()).thenReturn(expected);
		String result = middleManController.getLastReversedString();

		assertEquals(expected, result);
	}

}
