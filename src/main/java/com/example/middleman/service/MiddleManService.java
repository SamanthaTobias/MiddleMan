package com.example.middleman.service;

import com.example.middleman.Effect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class MiddleManService {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${basicapp.url}")
	private String basicAppUrl;

	private Effect effect = Effect.NONE;

	public void setBasicAppUrl(String basicAppUrl) {
		this.basicAppUrl = basicAppUrl;
	}

	public void setName(String name) {
		String url = basicAppUrl + "/setName/" + name;
		restTemplate.postForObject(url, null, String.class);
	}

	public void setEffect(String effect) {
		this.effect = Effect.valueOf(effect.toUpperCase());
	}

	public String getHelloMessage() {
		String helloMessage = callHelloOnBasicApp();
		return applyEffect(helloMessage);
	}

	private String callHelloOnBasicApp() {
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

	public Effect getEffect() {
		return effect;
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
		List<String> sortedWords = words.stream().sorted(String.CASE_INSENSITIVE_ORDER).collect(Collectors.toList());
		return String.join(" ", sortedWords);
	}

	public String reverseString(String input) {
		String url = basicAppUrl + "/reverse?input=" + input;
		return restTemplate.getForObject(url, String.class);
	}

	public String getLastReversedString() {
		String url = basicAppUrl + "/lastReversed";
		return restTemplate.getForObject(url, String.class);
	}

}

