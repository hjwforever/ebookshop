package com.aruoxi.ebookshop.controller.restController.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class BookContent {
  private Long bookId;
  private String bookName;
  private String author;
  private Integer totalPageNum;
  private Boolean isSinglePage;
  private List content;

  public BookContent(Long bookId, String bookName, String author, Integer totalPageNum, Boolean isSinglePage, List content) {
    this.bookId = bookId;
    this.bookName = bookName;
    this.author = author;
    this.totalPageNum = totalPageNum;
    this.isSinglePage = isSinglePage;
    this.content = content;
  }
}
