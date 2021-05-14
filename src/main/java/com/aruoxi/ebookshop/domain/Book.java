package com.aruoxi.ebookshop.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.Setter;
//import org.hibernate.annotations.Cache;
//import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.validation.annotation.Validated;

import io.swagger.v3.oas.annotations.media.Schema;

@Validated
@Entity
@Table(name = "ebooks")
@Getter
@Setter
public class Book implements Serializable {

  private static final long SERIAL_VERSION_UID = 4048798961366546485L;

  @Id
  @Column(name="book_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long bookId;

  @NotBlank
  @Size(max = 100)
  @Schema(description = "书籍名字", example = "Java从入门到如土", required = true)
  private String bookName;

  @Min(value = 0)
  @Max(value = 5)
  @Schema(description = "书籍评分", example = "4.5", defaultValue = "4.5")
  private Float starts;

  @Size(max = 100)
  @Schema(description = "书籍作者")
  private String author;

  @Size(max = 200)
  @Schema(description = "书籍介绍", example = "这是一本经典之作")
  private String bookIntro;

  @Column(name="book_uri")
  @Schema(description = "书籍 存储位置", example = "/books/1")
  private String bookUri;

  @Column(name="book_url")
  @Schema(description = "书籍 链接", example = "http://example.com/books/1")
  private String bookUrl;

  @Schema(description = "书籍封面图片", example = "http://example.com/books/1/CoverImg")
  private String bookCoverImg;

  @Schema(description = "书籍原价")
  private Float originalPrice;

  @Schema(description = "书籍现价")
  private Float sellingPrice;

  @Schema(description = "分类id")
  private Long bookCategoryId;

  @Schema(description = "书籍标签")
  private String tag;

  @Schema(description = "书籍销售状态", example = "售罄")
  private String bookSellStatus;

  @Schema(description = "创建时间")
  @CreationTimestamp
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date createTime;

  @Schema(description = "更新时间")
  @UpdateTimestamp
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date updateTime;

  public Book() {
    this.starts = 3f;
    this.originalPrice = 0f;
    this.sellingPrice = 0f;
    this.bookSellStatus = "热销";
    this.bookCoverImg = "https://cdn.jsdelivr.net/gh/hjwforever/images@main/img/defaultCoverImg.png";
    this.bookCategoryId = 1L;
    this.tag = "经典好书";
    this.author = "E-Book Shop";
    this.bookIntro = "暂无介绍";
  }

  public Book(Long bookId, @NotBlank @Size(max = 100) String bookName, @Min(value = 0) @Max(value = 5) Float starts, @Size(max = 100) String author, @Size(max = 200) String bookIntro, String bookUri, String bookCoverImg, Float originalPrice, Float sellingPrice, Long bookCategoryId, String tag, String bookSellStatus, Date createTime, Date updateTime) {
    this();
    this.bookId = bookId;
    this.bookName = bookName;
    this.starts = starts;
    this.author = author;
    this.bookIntro = bookIntro;
    this.bookUri = bookUri;
    this.bookCoverImg = bookCoverImg;
    this.originalPrice = originalPrice;
    this.sellingPrice = sellingPrice;
    this.bookCategoryId = bookCategoryId;
    this.tag = tag;
    this.bookSellStatus = bookSellStatus;
    this.createTime = createTime;
    this.updateTime = updateTime;
  }
}