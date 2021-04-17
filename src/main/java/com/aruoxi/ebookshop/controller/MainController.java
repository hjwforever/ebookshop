package com.aruoxi.ebookshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

  @GetMapping(value = {"/","/index", "/home"})
  public String root() {
    return "redirect:/books";
  }
}
