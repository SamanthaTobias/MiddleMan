package com.example.middleman.controller;

import com.example.middleman.service.MiddleManService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MiddleManController {

	@Autowired
	private MiddleManService middleManService;

	@PostMapping("/setName/{name}")
	public String setName(@PathVariable String name) {
		middleManService.setName(name);
		return "Name set to '" + name + "'";
	}

	@PostMapping("/setEffect/{effect}")
	public String setEffect(@PathVariable String effect) {
		middleManService.setEffect(effect);
		return "Effect set to '" + effect + "'";
	}

	@GetMapping("/hello")
	public String hello() {
		return middleManService.getHelloMessage();
	}

	@GetMapping("/reverse")
	public String reverseString(@RequestParam String input) {
		return middleManService.reverseString(input);
	}

	@GetMapping("/lastReversed")
	public String getLastReversedString() {
		return middleManService.getLastReversedString();
	}

}
