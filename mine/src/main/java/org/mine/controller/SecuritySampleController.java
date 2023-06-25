package org.mine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j;

@Log4j
@Controller
@RequestMapping("/security/*")
public class SecuritySampleController {
	@GetMapping("/all")
	public void all() {
		log.info("do all can access everybody");
	}
	
	@GetMapping("/member")
	public void member() {
		log.info("logined member");
	}
	
	@GetMapping("/admin")
	public void admin() {
		log.info("logined admin");
	}
}
