package com.webservices.restful_web_services.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@RequestMapping(path = "hello" ,method = RequestMethod.GET)
	public String hello() {
		return "hello";
	}
	
	@GetMapping(path = "/hello/var/{name}" )
	public String helloWorld(@PathVariable String name) {
		return "hello " + name;
	}
}
  