package com.github.mustsd.modules.demo;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author mustsd
 * @date 2024-12-24 15:37
 */
@Api(tags = "login")
@Controller
@RequestMapping("/demo")
@Slf4j
public class DemoController {

  @RequestMapping("/test")
  public ModelAndView appScreen() {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.addObject("name", "mustsd");
    modelAndView.setViewName("/index");
    return modelAndView;
  }
}
