package com.aruoxi.ebookshop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


/**
 * 查找书籍Dto
 */
@Data
@Schema
public class BookSearchDto {
  private String bookName;
  private String authorName;
  private Integer pageNum;
  private Integer pageSize;
  private Float minPrice;
  private Float maxPrice;
  private Boolean isExact;
  private String beforeYear;
  private String afterYear;

  public BookSearchDto(String bookName, String authorName, Integer pageNum, Integer pageSize, Float minPrice, Float maxPrice, Boolean isExact, String beforeYear, String afterYear) {
    this.bookName = bookName;
    this.authorName = authorName;
    this.pageNum = pageNum == null ? 1 : pageNum;
    this.pageSize = pageSize == null ? 10 : pageSize;
    this.minPrice = minPrice;
    this.maxPrice = maxPrice;
    this.isExact = isExact;
    this.beforeYear = beforeYear;
    this.afterYear = afterYear;
  }
}
