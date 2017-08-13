package com.jae.len.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jae.len.constants.UrlConstants;

@Controller
public class LePageController {
	
	@RequestMapping(value= UrlConstants.APP_RECITE_PAGE)
	public ModelAndView pageGoto(
			){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("recite");
		return modelAndView;
	}
}
