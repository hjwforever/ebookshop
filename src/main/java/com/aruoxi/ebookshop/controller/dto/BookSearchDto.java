package com.aruoxi.ebookshop.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.core.annotation.AliasFor;


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
  private Integer newPageNum;
  private String searchBookName;

  public BookSearchDto(String bookName,String searchBookName, String authorName, Integer pageNum, Integer pageSize, Float minPrice, Float maxPrice, Boolean isExact, String beforeYear, String afterYear, Integer newPageNum) {
    this.bookName = bookName == null ? "" : bookName;
    this.searchBookName = searchBookName == null ? "" : searchBookName;
    this.authorName = authorName == null ? "" : authorName;
    this.pageNum = pageNum == null ? 1 : pageNum;
    this.newPageNum = newPageNum == null ? 1 : newPageNum;
    this.pageSize = pageSize == null ? 10 : pageSize;
    this.minPrice = minPrice == null ? 0 : minPrice;
    this.maxPrice = maxPrice == null ? -1 : maxPrice; // -1表示无穷大
    this.isExact = isExact != null && isExact;
    this.beforeYear = beforeYear;
    this.afterYear = afterYear;
  }

}
