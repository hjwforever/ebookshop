package com.aruoxi.ebookshop.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 书籍上传Dto
 */
@Data
@Schema
public class BookUploadDto {
  private String bookName;
  private String authorName;
  private Float price;

  public BookUploadDto(String bookName, String authorName, Float price) {
    this.bookName = bookName != null ? bookName : "未知";
    this.authorName = authorName != null ? authorName : "ebookshop";
    this.price = price != null && price >= 0 ? price : 10;
  }
}
