package com.example.middleman;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class MiddleManService {

	@Value("${basicapp.url}")
	private String basicAppUrl;

	private Effect effect = Effect.NONE;

	public void setName(String name) {
		callSetNameOnBasicApp(name);
	}

	public void setEffect(String effect) {
		this.effect = Effect.valueOf(effect.toUpperCase());
	}

	public String getHelloMessage() {
		String helloMessage = callHelloOnBasicApp();
		return applyEffect(helloMessage);
	}

	private void callSetNameOnBasicApp(String name) {
		RestTemplate restTemplate = new RestTemplate();
		String url = basicAppUrl + "/setName/" + name;
		restTemplate.postForObject(url, null, String.class);
	}

	private String callHelloOnBasicApp() {
		RestTemplate restTemplate = new RestTemplate();
		String url = basicAppUrl + "/hello";
		System.out.println("callHelloOnBasicApp using url " + url);
		return restTemplate.getForObject(url, String.class);
	}

	private String applyEffect(String message) {
		return switch (effect) {
			case LOWERCASE -> message.toLowerCase();
			case UPPERCASE -> message.toUpperCase();
			case RANDOMCASE -> applyRandomCase(message);
			case ALPHABETIZE -> applyAlphabetize(message);
			default -> message;
		};
	}

	private String applyRandomCase(String message) {
		StringBuilder result = new StringBuilder();
		Random random = new Random();
		for (char c : message.toCharArray()) {
			if (Character.isAlphabetic(c)) {
				if (random.nextBoolean()) {
					result.append(Character.toUpperCase(c));
				} else {
					result.append(Character.toLowerCase(c));
				}
			} else {
				result.append(c);
			}
		}
		return result.toString();
	}

	private String applyAlphabetize(String message) {
		List<String> words = Arrays.asList(message.split(" "));
		List<String> sortedWords = words.stream().sorted().collect(Collectors.toList());
		return String.join(" ", sortedWords);
	}

}
