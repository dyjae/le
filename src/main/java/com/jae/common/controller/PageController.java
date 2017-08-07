package com.jae.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PageController {
	
	@RequestMapping(value= "/{url}.html")
	public ModelAndView pageGoto(
			@PathVariable("url")String url
			){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(url);
		return modelAndView;
	}
}
