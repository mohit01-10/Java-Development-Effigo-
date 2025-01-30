package com.my.webapps.fwebapp.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
	
	@Autowired
	private AuthenticationService authenticationService;
	
	//GET, POST 
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String gotoLoginPage() {
	//	model.put("name", name);
		return "login";
	}
	
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String gotoWelcomePage(@RequestParam String name, @RequestParam String password, ModelMap map) {
		
		if(authenticationService.authenticate(name, password))
		{
		
		map.put("name", name);
		map.put("password", password);
		return "welcome";
		}
		
		map.put("errorMessage", "Wrong username or password");
		
		return "login";
	}
}
