package com.aruoxi.ebookshop.controller.restController.dto;

import lombok.Data;

@Data
public class BookPage{
    private Integer index;
    private String content;

    public BookPage(Integer index, String content) {
      this.index = index;
      this.content = content;
    }
  }