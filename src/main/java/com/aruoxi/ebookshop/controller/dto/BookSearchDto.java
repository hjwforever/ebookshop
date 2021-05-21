package com.aruoxi.ebookshop.controller.dto;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.springframework.core.annotation.AliasFor;


/**
 * 查找书籍Dto
 */
@Data
@Schema(description = "书籍搜索DTO")
@Tag(name = "DTO")
public class BookSearchDto {
  @Schema(description = "模糊搜索 书籍名")
  private String bookName;

  @Schema(description = "模糊搜索 书籍名")
  private Long bookId;

  @Schema(description = "作者名")
  private String authorName;

  @Schema(description = "书籍列表 页码")
  private Integer pageNum;

  @Schema(description = "书籍列表 每页条数")
  private Integer pageSize;

  @Schema(description = "服务器是否有书籍资源 即能否阅读")
  private Boolean canRead;

  @Hidden
  @Schema(description = "最小价格")
  private Float minPrice;

  @Hidden
  @Schema(description = "最大价格")
  private Float maxPrice;

  @Hidden
  @Schema(description = "是否精确搜索")
  private Boolean isExact;

  @Hidden
  @Schema(description = "某年份之前")
  private String beforeYear;

  @Hidden
  @Schema(description = "某年份之后")
  private String afterYear;

  @Schema(description = "新页面序号")
  private Integer newPageNum;

  @Hidden
  @Schema(description = "搜索的书籍名")
  private String searchBookName;

  public BookSearchDto() {
    this.bookName = "";
    this.authorName = "";
    this.pageNum = 1;
    this.pageSize = 10;
    this.canRead = false;
  }

  public BookSearchDto(String bookName, Integer pageNum, Integer pageSize, Boolean canRead) {
    this();
    this.bookName = bookName;
    this.pageNum = pageNum;
    this.pageSize = pageSize;
    this.canRead = canRead;
  }

  public BookSearchDto(String bookName, Integer pageNum, Integer pageSize) {
    this(bookName, pageNum, pageSize, false);
  }

  public BookSearchDto(Integer pageNum, Integer pageSize, Boolean canRead) {
    this();
    this.pageNum = pageNum;
    this.pageSize = pageSize;
    this.canRead = canRead;
  }

  public BookSearchDto(Integer pageNum, Integer pageSize) {
    this(pageNum, pageSize, false);
  }

  public BookSearchDto(String bookName, Long bookId, String searchBookName, String authorName, Integer pageNum, Integer pageSize, Float minPrice, Float maxPrice, Boolean isExact, String beforeYear, String afterYear, Integer newPageNum) {
    this.bookName = bookName == null ? "" : bookName;
    this.bookId = bookId == null ? 0L : bookId;
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
