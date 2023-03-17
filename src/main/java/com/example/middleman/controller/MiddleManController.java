package com.example.middleman.controller;

import com.example.middleman.service.MiddleManService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
