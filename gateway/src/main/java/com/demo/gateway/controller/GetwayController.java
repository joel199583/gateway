package com.demo.gateway.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GetwayController {
	
	@PutMapping("/hi")
    public @ResponseBody Object selectAll() {
        return "forward:/en/new_fleets/set_default/2449436"; 
    }
}
