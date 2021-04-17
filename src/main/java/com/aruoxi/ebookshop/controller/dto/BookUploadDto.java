package com.aruoxi.ebookshop.controller.dto;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

/**
 * 书籍上传Dto
 */
@Data
@Schema(description = "书籍上传DTO")
@Tag(name = "DTO")
@Hidden
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
